	package it.uniroma3.siw.model;
	
	import java.time.LocalDate;
	
	import org.springframework.format.annotation.DateTimeFormat;
	
	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.OneToOne;
	import jakarta.persistence.Table;
	import jakarta.validation.constraints.NotBlank;
	import jakarta.validation.constraints.NotNull;
	
	@Entity
	@Table(name = "users") 
	public class User {
		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	
	    static final String DEFAULT_USER_PATH = "./images/defaultImage.jpg";
		
		public static final String COOK_ROLE = "COOK";
		public static final String ADMIN_ROLE = "ADMIN";
		
		
		@NotBlank
		private String name;
		@NotBlank
		private String surname;
		@NotBlank
		private String email;
		@NotBlank
		private String password;
		
		@DateTimeFormat(pattern = "dd-MM-yyyy")
		private LocalDate birthDate;
	
		private String imagePath;
	
		@NotNull
		private String role;
		
	    @OneToOne(mappedBy = "user")
		private Cook cook;
	
		
	
	    public User() {
			this.imagePath = DEFAULT_USER_PATH;
		}
	
		public Long getId() {
			return id;
		}
	
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public LocalDate getBirthDate() {
			return birthDate;
		}
	
		public void setBirthDate(LocalDate birthDate) {
			this.birthDate = birthDate;
		}
	
		public String getImagePath() {
			return imagePath;
		}
	
		public void setImagePath(String imagePath) {
			this.imagePath = imagePath;
		}
	
		public String getSurname() {
			return surname;
		}
		
		public void setSurname(String surname) {
			this.surname = surname;
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
	
		public String getRole() {
			return role;
		}
	
		public void setRole(String role) {
			this.role = role;
		}
	
		public Cook getCook() {
			return cook;
		}
	
		public void setCook(Cook cook) {
			this.cook = cook;
		}
	
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((surname == null) ? 0 : surname.hashCode());
			result = prime * result + ((email == null) ? 0 : email.hashCode());
			return result;
		}
	
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			User other = (User) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (surname == null) {
				if (other.surname != null)
					return false;
			} else if (!surname.equals(other.surname))
				return false;
			if (email == null) {
				if (other.email != null)
					return false;
			} else if (!email.equals(other.email))
				return false;
			return true;
		}
	}