package store.logic;

import java.util.List;

import store.users.PersonBoundary;
import store.users.PersonBoundaryWithoutPwd;

public interface UsersServiceInterface {
	public PersonBoundaryWithoutPwd createUser(PersonBoundary pBoundary);
	
	public PersonBoundaryWithoutPwd getUser(String email);
	
	public PersonBoundaryWithoutPwd login(String email, String pwd);
	
	public void updateUser(String email, PersonBoundary pBoundary);
	
	public void deleteAllUsers();
	
	public List<PersonBoundaryWithoutPwd> getAllBy(String criteriaType, String criteriaValue, int size, int page, String sortBy, String order);
}
