package it.uniroma3.siw.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;

@Service
public class IngredientService {
	
	

	public List<Ingredient> clearEmptyIngredients(List<Ingredient> ingredients, Recipe recipe) {
	    Iterator<Ingredient> iterator = ingredients.iterator();
	    while (iterator.hasNext()) {
	        Ingredient ingredient = iterator.next();
	        if (ingredient.getName().isEmpty() && ingredient.getQuantity().isEmpty()) {
	            iterator.remove();
	        } else {
	            ingredient.setRecipe(recipe);
	        }
	    }
	    return ingredients;
	}


}
