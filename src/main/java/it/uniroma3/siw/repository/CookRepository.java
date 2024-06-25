package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Recipe;

public interface CookRepository  extends CrudRepository<Recipe, Long>  {
	

    public Cook findByNameAndSurname(String name, String surname);

	
}
