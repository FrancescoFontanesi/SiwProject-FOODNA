package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService  {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected CredentialsRepository credentialsRepository;
    
   
    @Transactional
    public Credentials getCredentials(Long id) {
        Optional<Credentials> result = this.credentialsRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Credentials getCredentials(String email) {
        Optional<Credentials> result = this.credentialsRepository.findByEmail(email);
        return result.orElse(null);
    }


    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }
    
    @Transactional
    public String updatePassword(String psw) {
    	return this.passwordEncoder.encode(psw);
    }
    
    @Transactional
    public void deleteCredentials(Long credentialsId) {
        Credentials credentials = credentialsRepository.findById(credentialsId).orElse(null);
        if(credentials!=null) credentialsRepository.delete(credentials);
    }

    

    public boolean authenticateUser(String email, String password) {
        Credentials user = this.getCredentials(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true;
        }
        return false;
    }
}  
    
 
