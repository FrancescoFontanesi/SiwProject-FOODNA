package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.controller.validator.RecipeValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.IngredientService;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class RecipeController {

	
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private RecipeValidator recipeValidator;
	
	@Autowired 
	private CredentialsService credentialsService;
	

	@Autowired
	private CookService cookService;
	

	@Autowired
	private IngredientService ingredientService;

	@GetMapping("/recipes")
	public String getRecipes(Model model, Authentication auth) {
		model.addAttribute("c1", recipeService.findAllC("Primi"));
		model.addAttribute("c2", recipeService.findAllC("Secondi"));
		model.addAttribute("c3", recipeService.findAllC("Dessert"));
		return "recipes";
	}
	@GetMapping("/recipes/{category}")
	public String getRecipesByCategory(Model model, @PathVariable String category, @RequestParam(value = "Search", required = false) String search, Authentication auth) {
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
	public String getRecipe(@PathVariable("id") Long id, Model model, Authentication auth ) {
		model.addAttribute("recipe", recipeService.getRecipe(id));
        userService.addLoggedUser(auth, model);
        if (auth != null) {
            model.addAttribute("editRecipe",recipeService.CreateRecipeAndAddEmptyIngredients());
            Credentials credentials = credentialsService.getCredentials(auth.getName());
            if (!Credentials.ADMIN_ROLE.equals(credentials.getRole())) {
                model.addAttribute("liked", cookService.isRecipeLikedByLoggedCook(id, auth));
                
                Recipe recipe = recipeService.getRecipe(id);
                if (recipe.getCook() != null) {
                    model.addAttribute("ownerId", recipe.getCook().getId());
                    if(recipe.getCook().getId() == credentials.getUser().getCook().getId());
                }
            }
        }
       
		return "recipe";
	}
	
    @PreAuthorize("isAuthenticated()")
	@GetMapping("/addRecipe")
	public String showRecipeForm(Model model) {
	  
	    model.addAttribute("recipe", recipeService.CreateRecipeAndAddEmptyIngredients());
	    return "formNewRecipe";
	}
    
    @PreAuthorize("isAuthenticated()")
	@PostMapping("/addRecipe")
	public String submitRecipeForm(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult recipeBR,
	                               @RequestParam("file") MultipartFile preview, Authentication auth,
	                               RedirectAttributes redirectAttributes) {
	    
	    recipeService.addCookToRecipeForValidation(recipe, auth);
	    recipeValidator.validate(recipe, recipeBR);
	    
	    if (!recipeBR.hasErrors()) {
	    	recipe.setIngredients( ingredientService.clearEmptyIngredients(recipe.getIngredients(),recipe));
	        recipeService.saveRecipe(recipe, preview, auth);
	        return "redirect:/myProfile";
	    } else {
	    	System.out.println(recipeBR.toString());
	        redirectAttributes.addAttribute("error", true);
	        return "redirect:/addRecipe";
	    }
	}
    
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addToFavorites/{id}")
    public String addToFavorites(@PathVariable("id") Long id, Authentication auth) {
    	
    	User user = userService.getLoggedUser(auth);
    	Recipe likedRecipe = recipeService.getRecipe(id);
    	user.getCook().getFavoritesRecipes().add(likedRecipe);
    	
    	userService.saveUser(user);
    	
    	
    	return "redirect:/myProfile";
    }	
	
}
