package store.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import store.logic.UsersService;

@RestController
public class UsersController implements UsersService {
	private UsersService usersService;
	
	public UsersController() {}
	
	@Autowired
	public void setUsersService(UsersService service) {
		usersService = service;
	}

	@Override
	@RequestMapping(
			path = "/users",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonBoundary createUser(@RequestBody PersonBoundary pBoundary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RequestMapping(
			path = "/users/{email}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonBoundary getUser(@PathVariable("email") String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RequestMapping(
			path = "/users/login/{email}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public PersonBoundary login(
			@PathVariable("email") String email, 
			@RequestParam(name = "password", required = true) String pwd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RequestMapping(
			path = "/users/{email}",
			method = RequestMethod.GET,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@PathVariable("email") String email, @RequestBody PersonBoundary pBoundary) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@RequestMapping(
			path = "/users",
			method = RequestMethod.DELETE)
	public void deleteAllUsers() {
		// TODO Auto-generated method stub
	}

	@Override
	@RequestMapping(
			path = "/users/search",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PersonBoundary> getAllBy(
			@RequestParam(name = "criteriaType", required = true) String criteriaType, 
			@RequestParam(name = "criteriaValue", required = true) String criteriaValue, 
			@RequestParam(name = "size", required = false, defaultValue = "10") int size, 
			@RequestParam(name = "page", required = false, defaultValue = "0") int page) {
		// TODO Auto-generated method stub
		return null;
	}
}
