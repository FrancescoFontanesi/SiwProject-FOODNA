package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cook {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cook") 
	private List<Recipe> personal;


	@OneToMany(fetch = FetchType.LAZY) private List<Recipe> favorites;

	@OneToMany(fetch = FetchType.LAZY) private List<Ingredient> shoppingList;

	public Cook(User user) {
		this.personal = new ArrayList<>(); 
		this.favorites = new ArrayList<>();
		this.shoppingList = new ArrayList<>();
		this.user = user;
	}
	

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<Recipe> getPersonal() { return personal; }

	public void setPersonal(List<Recipe> personal) { this.personal = personal; }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Recipe> getFavorites() { return favorites; }

	public void setFavorites(List<Recipe> favorites) { this.favorites =
			favorites; }

	public List<Ingredient> getShoppingList() { return shoppingList; }

	public void setShoppingList(List<Ingredient> shoppingList) {
		this.shoppingList = shoppingList; }
}
