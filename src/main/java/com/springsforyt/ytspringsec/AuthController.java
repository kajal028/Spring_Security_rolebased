package com.springsforyt.ytspringsec;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    
    @GetMapping("/home")
    public String home() {
    return "home"; // Ensure home.html exists in src/main/resources/templates/
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Ensure login.html exists in templates/
    }


    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // Load register.html
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String firstname,
                               @RequestParam String lastname,
                               @RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String role,
                               Model model) {
        boolean success = userService.registerUser(firstname, lastname, username, email, password, role);
        
        if (success) {
            return "redirect:/register?success=true"; // Redirect with success message
        } else {
            return "redirect:/register?error=true"; // Redirect with error message
        }
    }



    @GetMapping("/admin/home")
    public String handleAdmin(){
        return "adminHome";  // HTML Page
    }

    @GetMapping("/user/home")
    public String handleUser(){
        return "userHome";  // HTML Page
    }
}

