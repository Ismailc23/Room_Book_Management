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

    @GetMapping("")
    public String viewHomePage()
    {
        return "HomePage";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "registerForm";
    }

    @PostMapping("/registrationMethod")
    public String registrationProcess(UserEntity user)
    {
        if(userRepository.existsByUserName(user.getUserName()))
        {
            return "registrationFailure";
        }
        userRepository.save(user);
        return "registrationSuccess";
    }
}
