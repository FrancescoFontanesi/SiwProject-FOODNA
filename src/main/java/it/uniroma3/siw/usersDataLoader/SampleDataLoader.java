package it.uniroma3.siw.usersDataLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.repository.CredentialsRepository;

@Component
public class SampleDataLoader implements CommandLineRunner {

  

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Creating a regular user
        User admin = new User();
        admin.setName("Adimin");
        admin.setSurname("");


        Credentials adminCredentials = new Credentials();
        adminCredentials.setEmail("admin@root.com");
        adminCredentials.setPassword(passwordEncoder.encode("pass"));
        adminCredentials.setRole(Credentials.ADMIN_ROLE);
        adminCredentials.setUser(admin);

        credentialsRepository.save(adminCredentials);
        
        User cook1 = new User();
        cook1.setName("Test name");
        cook1.setSurname("Test cognome");
        cook1.setBio("Test bio");
        cook1.setBirthDate("01/01/2000");
        cook1.setCook(new Cook());
        
      
        Credentials cook1Credentials = new Credentials();
        adminCredentials.setEmail("cook1@gmail.com");
        adminCredentials.setPassword(passwordEncoder.encode("pass"));
        adminCredentials.setRole(Credentials.COOK_ROLE);
        adminCredentials.setUser(cook1);

        credentialsRepository.save(cook1Credentials);



        System.out.println("Users data loaded.");
        
        
    }
}
