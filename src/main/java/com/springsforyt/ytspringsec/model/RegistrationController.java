// package com.springsforyt.ytspringsec.model;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// public class RegistrationController {
    
//     @Autowired
//     UserRepository repository;
    
//     @Autowired
//     PasswordEncoder encoder;

//     @PostMapping("/register/user")
//     public User createUser(@RequestBody User user){
//         user.setPassword(encoder.encode(user.getPassword()));
//         return repository.save(user);
//     }
// }
