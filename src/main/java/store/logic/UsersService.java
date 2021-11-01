package store.logic;

import java.util.List;

import store.users.PersonBoundary;

public interface UsersService {
	public PersonBoundary createUser(PersonBoundary pBoundary);
	
	public PersonBoundary getUser(String email);
	
	public PersonBoundary login(String email, String pwd);
	
	public void updateUser(String email, PersonBoundary pBoundary);
	
	public void deleteAllUsers();
	
	public List<PersonBoundary> getAllBy(String criteriaType, String criteriaValue, int size, int page);
}
