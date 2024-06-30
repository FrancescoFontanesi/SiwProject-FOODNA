package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.UserService;

@Controller
public class CookController {

	@Autowired
	private UserService userService;

	@GetMapping("/cooks")
	public String getCooks(Model m) {
		userService.addAllCooksUsers(m);
		return "cooks";	
	}
	
	
}
