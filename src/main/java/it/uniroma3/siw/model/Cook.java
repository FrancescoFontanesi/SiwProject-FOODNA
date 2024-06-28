package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cook {

	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cook") 
	private List<Recipe> personalRecipes;

	@OneToMany(fetch = FetchType.LAZY) private List<Recipe> favorites;

	@OneToMany(fetch = FetchType.LAZY) private List<Ingredient> shoppingList;

	public Cook() {
		this.personalRecipes = new ArrayList<>(); 
		this.favorites = new ArrayList<>();
		this.shoppingList = new ArrayList<>();
	}
	


	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<Recipe> getPersonal() { return personalRecipes; }

	public void setPersonal(List<Recipe> personal) { this.personalRecipes = personal; }


	public List<Recipe> getFavorites() { return favorites; }

	public void setFavorites(List<Recipe> favorites) { this.favorites =
			favorites; }

	public List<Ingredient> getShoppingList() { return shoppingList; }

	public void setShoppingList(List<Ingredient> shoppingList) {
		this.shoppingList = shoppingList; }


	@Override
	public String toString() {
		return ",  " + " personalRecipes =" + personalRecipes + ", favorites=" + favorites + ", shoppingList=" + shoppingList + ", ";
	}
}
