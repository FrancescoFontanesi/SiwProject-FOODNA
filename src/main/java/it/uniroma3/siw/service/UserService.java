package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Cook;
import it.uniroma3.siw.model.User;
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

    @Transactional
    public User getUser(Long id) {
        Optional<User> result = this.userRepository.findById(id);
        return result.orElse(null);
    }



    @Transactional
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Transactional
    public Iterable<User> getOnlyCooks() {
    	return userRepository.findAllByRole("COOK");
    }
    
    public void deleteAdimin(Long id) {
    	userRepository.deleteById(id);
    }
  

    @Transactional
    public User saveUser(User user) {
        user.setRole(User.COOK_ROLE);
        user.setCook(new Cook(user));
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }
}
