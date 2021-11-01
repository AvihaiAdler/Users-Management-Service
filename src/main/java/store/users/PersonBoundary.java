package store.users;

import java.util.Map;

public class PersonBoundary {
	String id;
	String email;
	String password;
	Map<String, String> name;
	String birthDate;
	String[] roles;
	
	public PersonBoundary() {}
	
	public PersonBoundary(String id, String email, String password, Map<String, String> name, String birthDate, String[] roles) {
		setId(id);
		setEmail(email);
		setPassword(password);
		setName(name);
		setBirthDate(birthDate);
		setRoles(roles);
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, String> getName() {
		return name;
	}

	public void setName(Map<String, String> name) {
		this.name = name;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	
	
}
