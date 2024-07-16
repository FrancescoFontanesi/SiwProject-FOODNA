package it.uniroma3.siw.controller;

import java.nio.file.AccessDeniedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class EditsController {
	
	
	@Autowired
	private UserService userService;
	
	@Autowired private CredentialsService credentialsService;

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
        Model model) throws AccessDeniedException {
		
		
		if(!credentialsService.emailExists(email)) {
        	userService.updateUser(id, email, password, name, surname, bio, birthDate, file);
		}
		else {
		redirectAttributes.addAttribute("error", true);
		}
		
		 return "redirect:/myProfile";
    }
	
	@GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id,Authentication auth) throws AccessDeniedException {
		
				
		if (id != credentialsService.getCredentials(auth.getName()).getUser().getId()) {
			throw new AccessDeniedException("Non autorizzato");
		}

		credentialsService.deleteCredentials(credentialsService.getCredentials(userService.findById(id)).getId());
        return "redirect:/logout";
    }


}
