package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.CookService;

@Controller
public class CookController {

	@Autowired
	private CookService cookService;

	@GetMapping("/cooks")
	public String getCooks(Model m) {
		m.addAttribute("cooks", cookService.addCooks());
		return "cooks";
		
	}
	
	
}