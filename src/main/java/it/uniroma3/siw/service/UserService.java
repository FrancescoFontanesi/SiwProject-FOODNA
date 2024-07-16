package it.uniroma3.siw.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UserRepository;
import jakarta.transaction.Transactional;


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
    protected CredentialsService credentialsService;
    
    @Autowired
	private CredentialsRepository credentialsRepository;
	
	
	public void addAllCooksUsers(Model m) {

		List<Credentials> cooksCredentialsList = credentialsRepository.findAllByRole(Credentials.COOK_ROLE);
		if (cooksCredentialsList.isEmpty()) {
			m.addAttribute("cooks", null);
		} else {
			List<User> userList = new ArrayList<User>();
			for (Credentials c : cooksCredentialsList) {
				userList.add(c.getUser());

			}
			
			System.out.println(userList);

			
			m.addAttribute("cooks", userList);
		}
	}
	
	public void addCooksByName(String search, Model model) {
		
		
		List<Credentials> cooksCredentialsList =  credentialsRepository.findAllByRole(Credentials.COOK_ROLE);
		List<User> userList = new ArrayList<User>();
		for (Credentials c : cooksCredentialsList) {
			if(c.getUser().getName().toLowerCase().contains(search.toLowerCase())) {
				userList.add(c.getUser());
			}
		}		
		model.addAttribute("cooks",userList);
		
	}
	
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	
    @Transactional
	public void updateUser(Long id, String email, String password, String name, String surname, String bio, String birthDate, MultipartFile file) {
		
    	
    	
    	
    	
		Credentials c = credentialsRepository.findById(id).get();
		User user = c.getUser();				
		
	
        if (!email.isBlank()) {
            c.setEmail(email);
        }
        if (!password.isBlank()) {
            c.setPassword(credentialsService.updatePassword(password));
        }
        
        if (!name.isBlank()) {
            user.setName(name);
        }
        if (!surname.isBlank()) {
            user.setSurname(surname);
        }
        if (!bio.isBlank()) {
            user.setBio(bio);
        }
        if (!birthDate.isBlank()) {
            user.setBirthDate(birthDate);
        }

        if (file != null && !file.isEmpty()) {
            storeFile(file,user);
        }
        
        
        c.setUser(user);
        System.out.println(c);
        credentialsRepository.save(c);
		}
		



	private void storeFile(MultipartFile p,User user) {
		
            try {
                byte[] bytes = p.getBytes();
                Path path = Paths.get(User.UPLOADED_FOLDER_PROFILEPICS + p.getOriginalFilename());
                System.out.println(path);
                Files.write(path, bytes);
                user.setProfilePic("/images/profilePics/"+ p.getOriginalFilename());
             } catch (IOException e) {
                e.printStackTrace();
           
        }
	}


	public User findById(Long id) {
		  
		User u = userRepository.findById(id).orElse(null);
		return u  ;
	}


	
	public User getLoggedUser(Authentication auth) {
		Credentials c = credentialsService.getCredentials(auth.getName());
		User u = c.getUser();
		return u;
	}
	
	public void addLoggedUser(Authentication auth,Model m ) {
		if(auth!=null) {
		Credentials c = credentialsService.getCredentials(auth.getName());
		m.addAttribute("loggedUser", c.getUser());
		}
		else 
			m.addAttribute("loggedUser",null);
	}

	
	public boolean isUserFollowedByLoggedUser(Long id, Authentication auth) {
		
		Cook cook = credentialsService.getCredentials(auth.getName()).getUser().getCook();
		return cook.getLikedCooks().contains(userRepository.findById(id).get());
		

	}

    
  

}
