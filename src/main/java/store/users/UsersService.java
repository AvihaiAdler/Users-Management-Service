package store.users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import store.dal.UsersDao;
import store.data.PersonEntity;
import store.exceptions.BadRequestException;
import store.exceptions.ForbiddenRequestException;
import store.exceptions.NotFoundException;
import store.exceptions.UnauthorizedException;
import store.logic.PersonEntityConverter;
import store.logic.UsersServiceInterface;
import store.miscellaneous.HelperClass;

/*
 * TODO:
 * implements checks for each criteria in public List<PersonBoundary> getAllBy
 */
@Service
public class UsersService implements UsersServiceInterface{
	private UsersDao usersDao;
	private PersonEntityConverter converter;
	
	@Autowired
	public void setDao(UsersDao dao) {
		this.usersDao = dao;
	}
	
	@Autowired
	public void setConverter(PersonEntityConverter converter) {
		this.converter = converter;
	}
	
	/*
	 * creates a new user entity and saves it in the DB
	 * if such email exists an exception will be thrown
	 */
	@Override
	@Transactional
	public PersonBoundaryWithoutPwd createUser(PersonBoundary pBoundary) {
		if(pBoundary == null)
			throw new BadRequestException("Null arguments received");
		if(!HelperClass.checkEmail(pBoundary.email))
			throw new BadRequestException("Invalid email address");
		if(!HelperClass.checkName(pBoundary.name))
			throw new BadRequestException("Invalid name");
		if(!HelperClass.checkPassword(pBoundary.password))
			throw new BadRequestException("Invalid password");
		if(!HelperClass.checkDate(pBoundary.birthDate))
			throw new BadRequestException("Invalid birth date");
		if(!HelperClass.checkRoles(pBoundary.roles))
			throw new BadRequestException("Ivalid roles");
		
		var optionalUsr = usersDao.findByEmail(pBoundary.email);
		if(optionalUsr.isPresent())
			throw new ForbiddenRequestException("User already exists");
		
		var entity = converter.toEntity(pBoundary);
		usersDao.save(entity);
		return converter.toBoundary(entity);
	}

	/*
	 * returns a boundary containing all user details (besides the password) who's email matches the received email
	 */
	@Override
	@Transactional(readOnly = true)
	public PersonBoundaryWithoutPwd getUser(String email) {
		if(!HelperClass.checkEmail(email))
			throw new BadRequestException("Invalid email address");
		
		var optionalUsr = usersDao.findByEmail(email);
		if(optionalUsr.isEmpty())
			throw new NotFoundException("User doesn't exists");
		
		return converter.toBoundary(optionalUsr.get());
	}

	/*
	 * returns a boundary containing all user details (besides the password) who's email and password matches the received email and password
	 */
	@Override
	@Transactional(readOnly = true)
	public PersonBoundaryWithoutPwd login(String email, String pwd) {
		if(!HelperClass.checkEmail(email))
			throw new BadRequestException("Invalid email address");
		if(!HelperClass.checkPassword(pwd))
			throw new BadRequestException("Invalid password");
		
		var optionalUsr = usersDao.findByEmail(email);
		if(optionalUsr.isEmpty())
			throw new NotFoundException("User doesn't exists");
		
		var usr = optionalUsr.get();
		if(!usr.getPassword().equals(pwd))
			throw new UnauthorizedException("Invalid operation");
		
		return converter.toBoundary(usr);
	}

	/*
	 * update the details of an existing user
	 */
	@Override
	@Transactional
	public void updateUser(String email, PersonBoundary pBoundary) {
		if(pBoundary == null)
			throw new BadRequestException("Null arguments received");
		if(!HelperClass.checkEmail(email))
			throw new BadRequestException("Invalid email address");
		
		var optionalUsr = usersDao.findByEmail(email);
		if(optionalUsr.isEmpty())
			throw new NotFoundException("User doesn't exists");
		
		var usrEntity = optionalUsr.get();
		if(HelperClass.checkEmail(pBoundary.email) && !pBoundary.email.equals(usrEntity.getEmail()))
			throw new ForbiddenRequestException("Changing an email address is not allowed");
		if(HelperClass.checkPassword(pBoundary.password))
			usrEntity.setPassword(pBoundary.password);
		if(pBoundary.name != null) {
			var firstName = pBoundary.name.get("first");
			var lastName = pBoundary.name.get("last");
			if(firstName != null)
				usrEntity.setFirstName(firstName);
			if(lastName != null)
				usrEntity.setLastName(lastName);
		}
		if(HelperClass.checkDate(pBoundary.birthDate))
			usrEntity.setBirthDate(LocalDate.parse(pBoundary.birthDate, DateTimeFormatter.ofPattern("dd-MM-yyy")));
		if(HelperClass.checkRoles(pBoundary.roles))
			usrEntity.setRoles(Arrays.asList(pBoundary.getRoles()).stream().reduce("", (a, b)-> a + b + "@@"));
		
		usersDao.save(usrEntity);
	}

	/*
	 * delete all users
	 */
	@Override
	@Transactional
	public void deleteAllUsers() {
		usersDao.deleteAll();
	}

	/*
	 * get all users who's criteria match the passed criteriaValue
	 * returns null if no such users found  
	 */
	@Override
	@Transactional(readOnly = true)
	public List<PersonBoundaryWithoutPwd> getAllBy(
			String criteriaType, String criteriaValue, 
			int size, int page, 
			String sortBy, String order) {
	
		if(!order.equalsIgnoreCase("ASC") && !order.equalsIgnoreCase("DESC"))
			throw new BadRequestException("Invalid sorting order");
		
		criteriaValue = criteriaValue.toLowerCase();
		
		String[] criteria = {"byemaildomain", "bybirthyear", "byrole", "none"};
		var criteriaLst = Arrays.asList(criteria);
		
		if(!criteriaLst.contains(criteriaType.toLowerCase()))
			throw new BadRequestException("Invalid criteria");
		
		Direction dir = order.equalsIgnoreCase("DESC") ? Direction.DESC : Direction.ASC; 
		
		List<PersonEntity> searchedUsrs = Collections.emptyList();
		if(criteriaType.equalsIgnoreCase(criteria[0]))	//search by domain
			searchedUsrs = usersDao.findByEmail_EndsWith(criteriaValue, PageRequest.of(page, size, dir, sortBy));
		if(criteriaType.equalsIgnoreCase(criteria[1]))	//search by year
			searchedUsrs = usersDao.findByBirthDate_Containing(criteriaValue, PageRequest.of(page, size, dir, sortBy));
		if(criteriaType.equalsIgnoreCase(criteria[2]))	//search by role
			searchedUsrs = usersDao.findByRoles_Containing(criteriaValue, PageRequest.of(page, size, dir, sortBy));
		if(criteriaType.equalsIgnoreCase(criteria[3]))
			searchedUsrs = usersDao.findAll(PageRequest.of(page, size, dir, sortBy)).getContent();
		
		return searchedUsrs
				.stream()
				.map(converter::toBoundary)
				.collect(Collectors.toList());
	}

}
