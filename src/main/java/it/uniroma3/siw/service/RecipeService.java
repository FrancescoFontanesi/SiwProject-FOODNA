package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.RecipeRepository;

@Service
public class RecipeService {
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	
	public List<Recipe> findAllC(String c){
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findAllByCategory(c);
		if(recipes.isEmpty()) return null; else return recipes;
	}
	
	
	public Recipe getRecipe(Long id) {
		Optional<Recipe> r = recipeRepository.findById(id);
		return r.orElse(null);
	}
	
	
	public List<Recipe> findFromSearch(String search, String string) {
		
		List<Recipe> recipes = (List<Recipe>) recipeRepository.findAllByCategory(string);
		System.out.println("Test" + recipes);
		List<Recipe> list = new ArrayList<>();
		for (Recipe recipe : recipes) {
			if(recipe.getName().toLowerCase().contains(search.toLowerCase()))
				list.add(recipe);
		}
		System.out.println("Test2" + list);
		return list;
	}

}
