package it.uniroma3.siw.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Credentials {

	
	public static final String ADMIN_ROLE = "ADMIN";
	public static final String COOK_ROLE = "COOK";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	
	@NotEmpty(message ="Email is not valid")
	public String email;
	@NotEmpty(message = "Password can not be empty")
	private String password;
	@NotEmpty
	private String role;

		
	@OneToOne(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
	private User user;
	
	public Credentials() {
		
	}


	public String getEmail() {
		return email;
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}


	@Override
	public String toString() {
		return "Credentials [id=" + id + ", email=" + email + ", password=" + password + ", role=" + role + ", user="
				+ user + "]";
	}

}
