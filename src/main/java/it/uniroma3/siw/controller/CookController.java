package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class CookController {

	@Autowired
	private UserService userService;
	
	@Autowired CredentialsService credentialsService;

	@Autowired
	private CookService cookService;
	
	

	@GetMapping("/cooks")
    public String getCooks(Model model, @RequestParam(value = "Search", required = false) String search,Authentication auth) {
		
		
        if (search != null && !search.isEmpty()) {
            userService.addCooksByName(search,model);
        } else {
            userService.addAllCooksUsers(model);
        }
        userService.addLoggedUser(auth, model);
        return "cooks"; // Assuming cooks.html is the view template
    }
	
	@GetMapping("/cook/{id}")
	public String getCook(@PathVariable ("id") Long id, Model m, Authentication auth) {
		m.addAttribute("user", userService.findById(id));
		userService.addLoggedUser(auth, m);
		if (auth != null && !credentialsService.isAdminLogged(auth)) {
		m.addAttribute("doesFollow", userService.isUserFollowedByLoggedUser(id, auth));
		}
		return "cook";
	}
	
    @PreAuthorize("isAuthenticated()")
	@PostMapping("/addFollow/{id}")
	public String addFollow(@PathVariable("id") Long id ,Authentication auth ) {
		User follower = userService.getLoggedUser(auth);
		User followed = userService.findById(id);
		follower.getCook().getLikedCooks().add(followed);
		followed.getCook().setNumberOfFollows(followed.getCook().getNumberOfFollows() + 1);
		return "/cook/" + id;
	}
	
	
	
}
