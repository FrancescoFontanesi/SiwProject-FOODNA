package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UserRepository;

/**
 * The UserService handles logic for Users.
 */
@Service
public class UserService {

    @Autowired
    protected UserRepository userRepository;
    
    @Autowired
    protected PasswordEncoder passwordEncoder;
    
    @Autowired
	private CredentialsRepository credentialsRepository;
	
	
	public void addAllCooksUsers(Model m){
		
		List<Credentials> cooksCredentialsList =  credentialsRepository.findAllByRole(Credentials.COOK_ROLE);
		List<User> userList = new ArrayList<User>();
		for (Credentials c : cooksCredentialsList) {
			userList.add(c.getUser());
			
		}
		
		m.addAttribute("cooks",userList);
	}
    
  

}
