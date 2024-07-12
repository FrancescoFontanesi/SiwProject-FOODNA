package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Recipe;

public interface RecipeRepository  extends CrudRepository<Recipe, Long>  {
	
	public Recipe findByName(String name);
	
	public Iterable<Recipe> findAllByCategory(String category);
	

	public List<Recipe> findByNameContainingIgnoreCase(String name);

	public List<Recipe> findByNameContainingIgnoreCaseAndCategory(String name, String category);
    
	
}
