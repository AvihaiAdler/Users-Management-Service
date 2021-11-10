package store.users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import store.data.PersonEntity;
import store.logic.PersonEntityConverter;

/*
 * @@ - acts as a separator between roles. e.g: manager$$admin$$... 
 * passwords gets returned as **** with the length of 8
 */
@Component
public class PersonEntityBoundaryConverter implements PersonEntityConverter{
//	private final int pwdLength = 8;
	
	@Override
	public PersonBoundaryWithoutPwd toBoundary(PersonEntity entity) {
		var boundary = new PersonBoundaryWithoutPwd();
		boundary.setEmail(entity.getEmail());
//		boundary.setPassword("*".repeat(pwdLength));
		
		boundary.setBirthDate(entity.getBirthdate().toString());
		
		var fullName = new TreeMap<String, String>();
		fullName.put("first", entity.getFirstname());
		fullName.put("last", entity.getLastname());
		boundary.setName(fullName);
		
		if(entity.getRoles() != null)
			boundary.setRoles(entity.getRoles().split("@@"));
		
		return boundary;
	}

	@Override
	public PersonEntity toEntity(PersonBoundary boundary) {
		var entity = new PersonEntity();
		entity.setEmail(boundary.getEmail());
		entity.setDomain(boundary.getEmail().split("@")[1]);
		entity.setPassword(boundary.getPassword());
		
		LocalDate date = LocalDate.parse(boundary.birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		entity.setBirthdate(date);
		
		entity.setFirstname(boundary.getName().get("first"));
		entity.setLastname(boundary.getName().get("last"));
		
		String roles = Arrays.asList(boundary.getRoles()).stream().reduce("", (a, b)-> a + b + "@@");
		
		if(roles != null)
			entity.setRoles(roles);
		
		return entity;
	}

}
