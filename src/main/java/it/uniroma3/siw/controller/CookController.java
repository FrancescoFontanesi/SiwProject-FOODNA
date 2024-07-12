package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UserService;

@Controller
public class CookController {

	@Autowired
	private UserService userService;
	
	@Autowired CredentialsService credentialsService;

	@GetMapping("/cooks")
    public String getCooks(Model model, @RequestParam(value = "Search", required = false) String search) {
		
		System.out.println(search);
		
        if (search != null && !search.isEmpty()) {
            userService.addCooksByName(search,model);
        } else {
            userService.addAllCooksUsers(model);
        }
        return "cooks"; // Assuming cooks.html is the view template
    }
	
	@GetMapping("/cook/{id}")
	public String getCook(@PathVariable ("id") Long id, Model m) {
		m.addAttribute("user", userService.findById(id));
		return "cook";
	}
	
	
	
}
