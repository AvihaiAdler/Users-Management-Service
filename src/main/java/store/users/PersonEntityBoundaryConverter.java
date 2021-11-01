package store.users;

import java.util.Arrays;
import java.util.TreeMap;
import java.util.UUID;

import store.data.PersonEntity;
import store.logic.PersonEntityConverter;


public class PersonEntityBoundaryConverter implements PersonEntityConverter{

	@Override
	public PersonBoundary toBoundary(PersonEntity entity) {
		var boundary = new PersonBoundary();
		boundary.setId(entity.getId());
		boundary.setEmail(entity.getEmail());
		boundary.setPassword("*".repeat(entity.getPassword().length()));
		boundary.setBirthDate(entity.getBirthDate());
		
		var fullName = new TreeMap<String, String>();
		fullName.put("first", entity.getFirstName());
		fullName.put("last", entity.getLastName());
		boundary.setName(fullName);
		
		boundary.setRoles(entity.getRoles().split(", "));
		
		return boundary;
	}

	@Override
	public PersonEntity toEntity(PersonBoundary boundary) {
		var entity = new PersonEntity();
		entity.setId(UUID.randomUUID().toString());
		entity.setEmail(boundary.getEmail());
		entity.setPassword(boundary.getPassword());
		entity.setBirthDate(boundary.getEmail());
		entity.setFirstName(boundary.getName().get("first"));
		entity.setLastName(boundary.getName().get("last"));
		
		String roles = Arrays.asList(boundary.getRoles()).stream().reduce("", (a, b)-> a + b + ", ");
		entity.setRoles(roles);
		
		return entity;
	}

}
