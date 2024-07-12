package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class AdminController {
	

	@Autowired
	private UserService userService;
	
	@Autowired CredentialsService credentialsService;

	
	@PostMapping("admin/updateUser/{id}")
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
		

        userService.updateUser(id, email, password, name, surname, bio, birthDate, file,model,redirectAttributes);
        
        return "redirect:/cooks";
    }
	
	
	@GetMapping("admin/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
		
		credentialsService.deleteCredentials(credentialsService.getCredentials(userService.findById(id)).getId());
        return "redirect:/cooks";
    }

}
