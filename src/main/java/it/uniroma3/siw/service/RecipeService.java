package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.RecipeRepository;

@Service
public class RecipeService {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	
	public List<Recipe> findAllC1(){
		return (List<Recipe>) recipeRepository.findAllByCategory("Primi");
	}
	public List<Recipe> findAllC2(){
		return (List<Recipe>) recipeRepository.findAllByCategory("Secondi");
	}
	public List<Recipe> findAllC3(){
		return (List<Recipe>) recipeRepository.findAllByCategory("Dessert");
	}
	
	public Recipe getRecipe(Long id) {
		Optional<Recipe> r = recipeRepository.findById(id);
		return r.orElse(null);
	}

}
