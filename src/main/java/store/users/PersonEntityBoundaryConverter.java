package store.users;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.UUID;

import store.data.PersonEntity;
import store.logic.PersonEntityConverter;

/*
 * @@ - acts as a separator between roles. e.g: manager$$admin$$... 
 * passwords gets returned as **** with the length of 8
 */

public class PersonEntityBoundaryConverter implements PersonEntityConverter{
	private final int pwdLength = 8;
	
	@Override
	public PersonBoundary toBoundary(PersonEntity entity) {
		var boundary = new PersonBoundary();
		boundary.setId(entity.getId());
		boundary.setEmail(entity.getEmail());
		boundary.setPassword("*".repeat(pwdLength));
		
		boundary.setBirthDate(entity.getBirthDate().toString());
		
		var fullName = new TreeMap<String, String>();
		fullName.put("first", entity.getFirstName());
		fullName.put("last", entity.getLastName());
		boundary.setName(fullName);
		
		boundary.setRoles(entity.getRoles().split("@@"));
		
		return boundary;
	}

	@Override
	public PersonEntity toEntity(PersonBoundary boundary) {
		var entity = new PersonEntity();
		entity.setId(UUID.randomUUID().toString());
		entity.setEmail(boundary.getEmail());
		entity.setDomain(boundary.getEmail().split("@")[1]);
		entity.setPassword(boundary.getPassword());
		
		LocalDate date = LocalDate.parse(boundary.birthDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		entity.setBirthDate(date);
		
		entity.setFirstName(boundary.getName().get("first"));
		entity.setLastName(boundary.getName().get("last"));
		
		String roles = Arrays.asList(boundary.getRoles()).stream().reduce("", (a, b)-> a + b + "@@");
		entity.setRoles(roles);
		
		return entity;
	}

}
