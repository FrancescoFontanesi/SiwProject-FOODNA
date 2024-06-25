package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.repository.RecipeRepository;

@Controller
public class RecipeController {

	
	@Autowired
	RecipeRepository recipeRepository;
	
	
	@GetMapping("/recipes")
	public String getRecipes(Model model) {
		model.addAttribute("recipes", recipeRepository.findAll());
		return "recipes.html";
	}
	
	@GetMapping("/recipes/{id}")
	public String getRecipe(@PathVariable("id") Long id, Model model ) {
		model.addAttribute("recipe", this.recipeRepository.findById(id).get());
		return "recipe.html";
	}
	
	
}
