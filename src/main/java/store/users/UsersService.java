package store.users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import store.dal.UsersDao;
import store.exceptions.BadRequestException;
import store.exceptions.ForbiddenRequestException;
import store.exceptions.NotFoundException;
import store.exceptions.UnauthorizedException;
import store.logic.PersonEntityConverter;
import store.logic.UsersServiceInterface;
import store.miscellaneous.HelperClass;

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
	public PersonBoundary createUser(PersonBoundary pBoundary) {
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
		
		var optionalUsr = usersDao.findAllByEmail(pBoundary.email);
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
	public PersonBoundary getUser(String email) {
		if(!HelperClass.checkEmail(email))
			throw new BadRequestException("Invalid email address");
		
		var optionalUsr = usersDao.findAllByEmail(email);
		if(optionalUsr.isEmpty())
			throw new NotFoundException("User doesn't exists");
		
		return converter.toBoundary(optionalUsr.get());
	}

	/*
	 * returns a boundary containing all user details (besides the password) who's email and password matches the received email and password
	 */
	@Override
	@Transactional(readOnly = true)
	public PersonBoundary login(String email, String pwd) {
		if(!HelperClass.checkEmail(email))
			throw new BadRequestException("Invalid email address");
		if(!HelperClass.checkPassword(pwd))
			throw new BadRequestException("Invalid password");
		
		var optionalUsr = usersDao.findAllByEmail(email);
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
		
		var optionalUsr = usersDao.findAllByEmail(email);
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
	}

	@Override
	@Transactional
	public void deleteAllUsers() {
		usersDao.deleteAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<PersonBoundary> getAllUsers(int size, int page, String sortBy, String order) {
		if(!order.equalsIgnoreCase("ASC") || order.equalsIgnoreCase("DESC"))
			throw new BadRequestException("Invalid order");
		
		Direction dir = order.equalsIgnoreCase("DESC") ? Direction.DESC : Direction.ASC; 
		var allUsers = usersDao.findAll(PageRequest.of(page, size, dir, sortBy));
		return StreamSupport
				.stream(allUsers.spliterator(), false)
				.map(u -> converter.toBoundary(u))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public List<PersonBoundary> getAllBy(String criteriaType, String criteriaValue, int size, int page, String sortBy,
			String order) {
		// TODO Auto-generated method stub
		return null;
	}

}
