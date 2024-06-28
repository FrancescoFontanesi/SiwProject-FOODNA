package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Recipe;
import it.uniroma3.siw.repository.CookRepository;

@Service
public class CookService {
     
	@Autowired
	private CookRepository cookRepository;

	public Iterable<Cook> addCooks() {
	     
		return cookRepository.findAll();
	}

}
