package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
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

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.controller.validator.RecipeValidator;
import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AdminService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.IngredientService;
import it.uniroma3.siw.service.RecipeService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AdminController {
	

	@Autowired
	private UserService userService;
	
	@Autowired CredentialsService credentialsService;

	@Autowired
	private CredentialsValidator credentialsValidator;

	
	@Autowired
	private AdminService adminService;

	@Autowired
	private RecipeService recipeService;


	@Autowired
	private IngredientService ingredientService;

	
	@PostMapping("/admin/updateUser/{id}")
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
        Model model) {
		
		if(!credentialsService.emailExists(email)) {
        	userService.updateUser(id, email, password, name, surname, bio, birthDate, file);
		}
		else {
		redirectAttributes.addAttribute("errorID",id);
		}
        return "redirect:/cooks";
    }
	
	
	@PostMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
		credentialsService.deleteCredentials(credentialsService.getCredentials(userService.findById(id)).getId());
        return "redirect:/cooks";
    }
	
	@PostMapping("/admin/addNewUser")
	public String addNewUser(@Valid @ModelAttribute("Credentials") Credentials c,
			@Valid @ModelAttribute("newUser") User u, @RequestParam("file") MultipartFile profilePic, BindingResult userBR,
			BindingResult credentialsBR,RedirectAttributes redirectAttributes) throws IOException {
        
		
		
		this.credentialsValidator.validate(c, credentialsBR);
		if (!userBR.hasErrors() && !credentialsBR.hasErrors()) {
			if(c.getRole()=="Cook") u.setCook(new Cook());
			credentialsService.saveUserCredentialsForAdmin(c, u, profilePic);
		}
		else
        redirectAttributes.addFlashAttribute("userError", true);
		return "redirect:/myProfile";
	}
	
	@PostMapping("admin/addRecipe")
	public String submitRecipeForm(@Valid @ModelAttribute("recipe") Recipe recipe, BindingResult recipeBR,
	                               @RequestParam("file") MultipartFile preview, Authentication auth,
	                               RedirectAttributes redirectAttributes) {
	    
	    
	    if (!recipeBR.hasErrors()) {
	    	recipe.setIngredients( ingredientService.clearEmptyIngredients(recipe.getIngredients(),recipe));
	        recipeService.saveRecipeForAdmin(recipe, preview);
	    } else {
	        redirectAttributes.addFlashAttribute("recipeError", true);
	    }
        return "redirect:/myProfile";
	}
	
	@PostMapping("/admin/wipeCooks")
	public String wipeCooks(Model m) {
		adminService.wipeUsersWithRoleCook();
		m.addAttribute("cooksWipeSucces", true);
		
		return "redirect:/myProfile";
		
	}
	
	@PostMapping("/admin/wipeRecipes")
	public String wipeRecipes(Model m) {
		adminService.wipeRecipes();
		m.addAttribute("recipesWipeSucces", true);
		return "redirect:/myProfile";
		
	}
}
