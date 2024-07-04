package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.service.RecipeService;

@Controller
public class RecipeController {

	
	
	@Autowired
	private RecipeService recipeService;

	@GetMapping("/recipes")
	public String getRecipes(Model model) {
		model.addAttribute("c1", recipeService.findAllC1());
		model.addAttribute("c2", recipeService.findAllC2());
		model.addAttribute("c3", recipeService.findAllC3());
		return "recipes.html";
	}
	@GetMapping("/recipes/c1")
	public String getRecipesC1(Model model) {
		model.addAttribute("c1", recipeService.findAllC1());
		return "c1.html";
	}
	@GetMapping("/recipes/c2")
	public String getRecipesc2(Model model) {
		model.addAttribute("c2", recipeService.findAllC2());
		return "c2.html";
	}
	
	@GetMapping("/recipes/c3")
	public String getRecipesc3(Model model) {
		model.addAttribute("c3", recipeService.findAllC3());
		return "c3.html";
	}
	
	@GetMapping("/recipe/{id}")
	public String getRecipe(@PathVariable("id") Long id, Model model ) {
		model.addAttribute("recipe", recipeService.getRecipe(id));
		System.out.println(recipeService.getRecipe(id).toString());
		return "recipe.html";
	}
	
	
}
