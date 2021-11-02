package store.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import store.dal.UsersDao;
import store.exceptions.BadRequestException;
import store.exceptions.ForbiddenRequestException;
import store.logic.PersonEntityConverter;
import store.logic.UsersServiceInterface;
import store.miscellaneous.HelperClass;

@Service
public class UsersService implements UsersServiceInterface{
	private UsersDao dao;
	private PersonEntityConverter converter;
	
	@Autowired
	public void setDao(UsersDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	public void setConverter(PersonEntityConverter converter) {
		this.converter = converter;
	}
	
	@Override
	public PersonBoundary createUser(PersonBoundary pBoundary) {
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
		
		var optionalUsr = dao.findAllByEmail(pBoundary.email);
		if(optionalUsr.isPresent())
			throw new ForbiddenRequestException("User already exists");
		
		var entity = converter.toEntity(pBoundary);
		dao.save(entity);
		return converter.toBoundary(entity);
	}

	@Override
	public PersonBoundary getUser(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersonBoundary login(String email, String pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(String email, PersonBoundary pBoundary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllUsers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PersonBoundary> getAllUsers(int size, int page, String sortBy, String order) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonBoundary> getAllBy(String criteriaType, String criteriaValue, int size, int page, String sortBy,
			String order) {
		// TODO Auto-generated method stub
		return null;
	}

}
