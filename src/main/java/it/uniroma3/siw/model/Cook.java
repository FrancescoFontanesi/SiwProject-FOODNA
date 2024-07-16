package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cook {

	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

	@OneToOne
    private User user;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cook", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Recipe> personalRecipes;

    @OneToMany(fetch = FetchType.LAZY) 
    private List<Recipe> favoritesRecipes;

    @OneToMany(fetch = FetchType.LAZY) 
    private List<User> likedCooks;
	

	/*
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy = "cook", cascade =
	 * {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true) private
	 * List<Recipe> personalRecipes;
	 * 
	 * 
	 * //uso id lista Long non ottimizzerebbe la ricerca
	 * 
	 * @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
	 * CascadeType.MERGE}, orphanRemoval = true) private List<Recipe>
	 * favoritesRecipes;
	 * 
	 * @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,
	 * CascadeType.MERGE}, orphanRemoval = true) private List<User> likedCooks;
	 */
	
	private Integer numberOfFollows;
	
	public Cook() {
		this.personalRecipes = new ArrayList<>(); 
		this.favoritesRecipes = new ArrayList<>();
		this.likedCooks = new ArrayList<>();
		this.numberOfFollows = 0;
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

	public List<Recipe> getPersonalRecipes() {
		return personalRecipes;
	}

	public void setPersonalRecipes(List<Recipe> personalRecipes) {
		this.personalRecipes = personalRecipes;
	}

	public List<Recipe> getFavoritesRecipes() {
		return favoritesRecipes;
	}

	public void setFavoritesRecipes(List<Recipe> favoritesRecipes) {
		this.favoritesRecipes = favoritesRecipes;
	}

	public List<User> getLikedCooks() {
		return likedCooks;
	}

	public void setLikedCooks(List<User> likedCooks) {
		this.likedCooks = likedCooks;
	}

	public Integer getNumberOfFollows() {
		return numberOfFollows;
	}

	public void setNumberOfFollows(Integer numberOfFollows) {
		this.numberOfFollows = numberOfFollows;
	}

	@Override
	public String toString() {
		return "personalRecipes=" + personalRecipes + ", favoritesRecipes=" + favoritesRecipes + ", likedCooks="
				+ likedCooks + ", numberOfFollows=" + numberOfFollows;
	}

	
   
	

	
	
	
	
}

