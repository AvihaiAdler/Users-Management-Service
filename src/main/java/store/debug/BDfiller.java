package store.debug;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import store.logic.UsersServiceInterface;
import store.users.PersonBoundary;

@Component
public class BDfiller implements CommandLineRunner{
	private UsersServiceInterface usersService;
	
	@Autowired
	public void setService(UsersServiceInterface usersService) {
		this.usersService = usersService;
	}
	
	@Override
	public void run(String... args) throws Exception {
		String name = "a";
		String domain = "@demo.com";
		int year = 1990;
		String[] roles = {"aa", "bb"}; 
		
		IntStream.range(1, 20)
			.mapToObj(i -> {
				Map<String, String> n = new HashMap<>();
				n.put("first", name+i);
				n.put("last", name+i);
				return new PersonBoundary(name + i + domain, "a1111", n, "01-01-" + Integer.toString(year+i), roles);
				})
			.forEach(user -> usersService.createUser(user));	
	}
	
}
