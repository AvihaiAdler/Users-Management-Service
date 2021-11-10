package store.data;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERS")
public class PersonEntity {
	String email;
	String domain;
	String password;
	String firstname, lastname;
	LocalDate birthDate;
	String roles;
	
	public PersonEntity() {}
	
	@Id	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	@Column(name = "FISRTNAME")
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	@Column(name = "LASTNAME")
	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "BIRTHDATE")
	public LocalDate getBirthdate() {
		return birthDate;
	}

	public void setBirthdate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getRoles() {
		return roles;
	}
	
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	
}
