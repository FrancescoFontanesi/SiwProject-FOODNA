package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.net.SyslogOutputStream;
import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CookRepository;
import it.uniroma3.siw.repository.RecipeRepository;

@Service
public class CookService {
	
	@Autowired
	private CookRepository cookRepository;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired 
	private RecipeRepository recipeRepository;
	
	public boolean isRecipeLikedByLoggedCook(Long id,Authentication auth) {
		
		Cook cook = credentialsService.getCredentials(auth.getName()).getUser().getCook();
		return cook.getFavoritesRecipes().contains(recipeRepository.findById(id).get());
		}
	
	public void saveCook(Cook cook) {
		cookRepository.save(cook);
	}
     
	
}
