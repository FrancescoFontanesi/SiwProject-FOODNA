package it.uniroma3.siw.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Ingredient;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CookRepository;
import it.uniroma3.siw.repository.RecipeRepository;
import jakarta.transaction.Transactional;

@Service
public class RecipeService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private CookRepository cookRepository;

	@Autowired
	private IngredientService ingredientService;
	
	
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

	
	private void storeFile(MultipartFile p,Recipe recipe) {
		
		
		if(!p.isEmpty()) {
        try {
            byte[] bytes = p.getBytes();
            Path path = Paths.get(Recipe.UPLOADED_FOLDER_RECIPE + p.getOriginalFilename());
            System.out.println(path);
            Files.write(path, bytes);
            recipe.setMainImagePath("/images/recipes/"+ p.getOriginalFilename());
         } catch (IOException e) {
            e.printStackTrace();
       
         }
        
        
    }
}
	
	public void addCookToRecipeForValidation( Recipe recipe, Authentication auth) {
		
		User user = userService.getLoggedUser(auth);
		Cook currentCook = user.getCook();
		
		recipe.setCook(currentCook);
	}
	
	public Recipe CreateRecipeAndAddEmptyIngredients() {
		Recipe recipe = new Recipe();
	    for (int i = 0; i < 5; i++) {
	        recipe.getIngredients().add(new Ingredient());
	    }
	    
	    return recipe;
	}

	@Transactional
	public void saveRecipe(Recipe recipe, MultipartFile file,Authentication auth) {
		
		storeFile(file, recipe);
		
		User user = userService.getLoggedUser(auth);
		Cook currentCook = user.getCook();
		currentCook.getPersonalRecipes().add(recipe);
		
		cookRepository.save(currentCook);
	}
	
	@Transactional
	public void saveRecipeForAdmin(Recipe recipe, MultipartFile file) {
		
		storeFile(file, recipe);
		recipeRepository.save(recipe);
		
		
	}
	
	@Transactional
	public void updateRecipe(Recipe oldRecipe, Recipe newRecipe, MultipartFile file ) {

		
		if (newRecipe.getName() != null && !newRecipe.getName().isBlank()) {
	        oldRecipe.setName(newRecipe.getName());
	    }

	    if (newRecipe.getDescription() != null && !newRecipe.getDescription().isBlank()) {
	        oldRecipe.setDescription(newRecipe.getDescription());
	    }

	    if (newRecipe.getCategory() != null && !newRecipe.getCategory().isBlank()) {
	        oldRecipe.setCategory(newRecipe.getCategory());
	    }

	    List<Ingredient> newIngredients = ingredientService.clearEmptyIngredients(newRecipe.getIngredients(), oldRecipe);
	    if (!newIngredients.isEmpty()) {
	        oldRecipe.getIngredients().clear();
	        oldRecipe.getIngredients().addAll(newIngredients);
	    }

	    if (!file.isEmpty()) {
	        storeFile(file,oldRecipe);
	    }
	    
	    recipeRepository.save(oldRecipe);
    }

    @Transactional
	public void deleteRecipe(Recipe recipe) {
                recipeRepository.delete(recipe);		
	}


	
	
}
