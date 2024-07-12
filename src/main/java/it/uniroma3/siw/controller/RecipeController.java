package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.service.RecipeService;

@Controller
public class RecipeController {

	
	
	@Autowired
	private RecipeService recipeService;

	@GetMapping("/recipes")
	public String getRecipes(Model model) {
		model.addAttribute("c1", recipeService.findAllC("Primi"));
		model.addAttribute("c2", recipeService.findAllC("Secondi"));
		model.addAttribute("c3", recipeService.findAllC("Dessert"));
		return "recipes.html";
	}
	@GetMapping("/recipes/{category}")
	public String getRecipesByCategory(Model model, @PathVariable String category, @RequestParam(value = "Search", required = false) String search) {
	    List<Recipe> recipes;
	    if (search != null && !search.isEmpty()) {
	        recipes = recipeService.findFromSearch(search, category);
	    } else {
	        recipes = recipeService.findAllC(category);
	    }
	    model.addAttribute("recipes", recipes);
	    return category.toLowerCase(); 
	}

	
	@GetMapping("/recipe/{id}")
	public String getRecipe(@PathVariable("id") Long id, Model model ) {
		model.addAttribute("recipe", recipeService.getRecipe(id));
		System.out.println(recipeService.getRecipe(id).toString());
		return "recipe.html";
	}
	
	
}
