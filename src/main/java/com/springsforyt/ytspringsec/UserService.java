package com.springsforyt.ytspringsec;

    
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsforyt.ytspringsec.model.User;
import com.springsforyt.ytspringsec.model.UserRepository;

@Service
public class UserService {

    private final  UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public boolean registerUser(String firstname, String lastname, String username, String email, String password, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false; // Username already exists
        }

        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Encrypt password
        user.setRole(role);
        
        userRepository.save(user);
        return true;
    }
}
