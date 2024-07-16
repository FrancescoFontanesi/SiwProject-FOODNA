package it.uniroma3.siw.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.AdminService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.RecipeService;
import jakarta.validation.Valid;

@Controller
@Validated
public class AuthController {

	@Autowired
	private CredentialsService credentialsService;

	
	
	@Autowired
	private RecipeService recipeService;
	
    @Autowired
    private CredentialsValidator credentialsValidator;


	@GetMapping("/login")
	public String showLoginForm() {
		return "login";
	}


	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("user", new User());
		return "formRegister";
	}

	@PostMapping("/register")
	public String registerUser(@Valid @ModelAttribute("Credentials") Credentials c,
			@Valid @ModelAttribute("User") User u, @RequestParam("file") MultipartFile profilePic, BindingResult userBR,
			BindingResult credentialsBR,RedirectAttributes redirectAttributes) throws IOException {
        
		this.credentialsValidator.validate(c, credentialsBR);
		if (!userBR.hasErrors() && !credentialsBR.hasErrors()) {
			credentialsService.saveUserCredentials(c, u, profilePic);
			return "redirect:/login";

		}
		
        redirectAttributes.addAttribute("error", true);
		return "redirect:/register";
	}

	

	@GetMapping("/myProfile")
	public String myPage(Authentication auth, Model model) {

		Credentials c = credentialsService.getCredentials(auth.getName());
		User u = c.getUser();
		model.addAttribute("user", u);

		switch (c.getRole()) {
		case Credentials.COOK_ROLE:	
			return "cookPersonalPage";
		case Credentials.ADMIN_ROLE:
			model.addAttribute("credentials", new Credentials());
			model.addAttribute("newUser", new User());
			model.addAttribute("recipe", recipeService.CreateRecipeAndAddEmptyIngredients() );
			return "adminPage";
		default:
			return "redirect:/login";
		}
	}

}
