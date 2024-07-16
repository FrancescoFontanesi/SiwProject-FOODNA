package it.uniroma3.siw.controller;

import java.nio.file.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.controller.validator.RecipeValidator;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.IngredientService;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class EditsController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired private CredentialsService credentialsService;

	@Autowired
	private RecipeService recipeService;

	@PostMapping("/updateUser/{id}")
    public String updateUser(
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        @RequestParam("name") String name,
        @RequestParam("surname") String surname,
        @RequestParam("bio") String bio,
        @RequestParam("birthDate") String birthDate,
        @RequestParam("file") MultipartFile file,
        @PathVariable("id") Long id,
        RedirectAttributes redirectAttributes,
        Model model,Authentication auth) throws AccessDeniedException {
		
		if (id != credentialsService.getCredentials(auth.getName()).getUser().getId()  || auth==null) {
			throw new AccessDeniedException("Non autorizzato");
		}
		
		
		if(!credentialsService.emailExists(email)) {
        	userService.updateUser(id, email, password, name, surname, bio, birthDate, file);
		}
		else {
		redirectAttributes.addAttribute("error", true);
		}
		
		 return "redirect:/myProfile";
    }
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/editRecipe/{id}")
	public String submitRecipeForm(@PathVariable("id") Long id,@Valid @ModelAttribute("editRecipe") Recipe newRecipe, BindingResult recipeBR,
	                               @RequestParam("file") MultipartFile preview, Authentication auth,
	                               RedirectAttributes redirectAttributes) throws AccessDeniedException {
		
		

		if (auth==null || recipeService.getRecipe(id).getCook()==null || (recipeService.getRecipe(id).getCook().getId() != credentialsService.getCredentials(auth.getName()).getUser().getCook().getId())) {
			throw new AccessDeniedException("Non autorizzato");
		}
	 
	    
	    if (!recipeBR.hasErrors()) {
	    	recipeService.updateRecipe(recipeService.getRecipe(id), newRecipe, preview);
	    } else {
	        redirectAttributes.addAttribute("error", true);
	    }
	    return "redirect:/recipe/" + id;
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id,Authentication auth) throws AccessDeniedException {
		
				
		if (id != credentialsService.getCredentials(auth.getName()).getUser().getId()  || auth==null) {
			throw new AccessDeniedException("Non autorizzato");
		}

		credentialsService.deleteCredentials(credentialsService.getCredentials(userService.findById(id)).getId());
        return "redirect:/logout";
    }
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/deleteRecipe/{id}")
    public String deleteRecipe(@PathVariable("id") Long id,Authentication auth) throws AccessDeniedException {
		
		if (auth==null || recipeService.getRecipe(id).getCook()==null || (recipeService.getRecipe(id).getCook().getId() != credentialsService.getCredentials(auth.getName()).getUser().getCook().getId())) {
			throw new AccessDeniedException("Non autorizzato");
		}
		recipeService.deleteRecipe(recipeService.getRecipe(id));
        return "redirect:/myProfile";
    }
	
	


}
