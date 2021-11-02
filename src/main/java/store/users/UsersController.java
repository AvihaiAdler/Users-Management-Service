package store.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import store.logic.UsersServiceInterface;

@RestController
public class UsersController {
	private UsersServiceInterface usersService;
	
	public UsersController() {}
	
	@Autowired
	public void setUsersService(UsersServiceInterface service) {
		usersService = service;
	}

	@RequestMapping(
			path = "/users",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonBoundary createUser(@RequestBody PersonBoundary pBoundary) {
		return usersService.createUser(pBoundary);
	}

	@RequestMapping(
			path = "/users/{email}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonBoundary getUser(@PathVariable("email") String email) {
		return usersService.getUser(email);
	}

	@RequestMapping(
			path = "/users/login/{email}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonBoundary login(
			@PathVariable("email") String email, 
			@RequestParam(name = "password", required = true) String pwd) {
		return usersService.login(email, pwd);
	}

	@RequestMapping(
			path = "/users/{email}",
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@PathVariable("email") String email, @RequestBody PersonBoundary pBoundary) {
		usersService.updateUser(email, pBoundary);
	}

	@RequestMapping(
			path = "/users",
			method = RequestMethod.DELETE)
	public void deleteAllUsers() {
		usersService.deleteAllUsers();
	}

	@RequestMapping(
			path = "/users/search",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonBoundary[] getAllUsers(
			@RequestParam(name = "size", required = false, defaultValue = "10") int size, 
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "email") String sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") String order) {
		return usersService.getAllUsers(size, page, sortBy, order).toArray(new PersonBoundary[0]);
	}
	
	@RequestMapping(
			path = "/users/search",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonBoundary[] getAllUsersBy(
			@RequestParam(name = "criteriaType", required = true) String criteriaType, 
			@RequestParam(name = "criteriaValue", required = true) String criteriaValue, 
			@RequestParam(name = "size", required = false, defaultValue = "10") int size, 
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "sortBy", required = false, defaultValue = "email") String sortBy,
			@RequestParam(name = "sortOrder", required = false, defaultValue = "ASC") String order) {
		return usersService.getAllBy(criteriaType, criteriaValue, size, page, sortBy, order).toArray(new PersonBoundary[0]);
	}
}
