package com.rest.contoller;

import com.rest.Entity.UserEntity;
import com.rest.ExceptionHandling.CustomerExceptions.UserNameAlreadyException;
import com.rest.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WebController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String viewHomePage()
    {
        return "LoginPage";
    }

    @GetMapping("/signUp")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "SignUpPage";
    }

    @PostMapping("/registrationMethod")
    public String registrationProcess(UserEntity user, Model model) {
        if (userRepository.existsByUserName(user.getUserName())) {
            model.addAttribute("errorMessage", "Username already exists");
            model.addAttribute("user", user);
            return "SignUpPage";
        }
        userRepository.save(user);
        return "LoginPage";
    }
}
