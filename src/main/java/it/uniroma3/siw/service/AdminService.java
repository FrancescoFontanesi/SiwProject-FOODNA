package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CookRepository;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.RecipeRepository;


@Service
public class AdminService {
	
	
	@Autowired
	private CredentialsRepository credentialsRepository;
	
	@Autowired
	private CookRepository cookRepository;

	@Autowired
	private RecipeRepository recipeRepository;

	public void loadAllUserCredentials(Model model) {
           model.addAttribute("usersCredentials", credentialsRepository.findAllByRole(Credentials.COOK_ROLE));		
	}
	
	
	public void resetStats() {
		for (Cook c : cookRepository.findAll()) {
			c.setNumberOfFollows(0);
		}
	}
	
	public void resetRecipesForAll() {
		for (Cook c : cookRepository.findAll()) {
			c.getPersonalRecipes().clear();;
		}
	}
	
	public void wipeUsersWithRoleCook() {
        List<Credentials> usersWithCookRole = credentialsRepository.findAllByRole("COOK"); // Adjust based on your method for fetching users
        for (Credentials user : usersWithCookRole) {
        	credentialsRepository.delete(user);
        }
    }
	
	public void wipeRecipes() {
		recipeRepository.deleteAll();
        }
    }
	

	
	

