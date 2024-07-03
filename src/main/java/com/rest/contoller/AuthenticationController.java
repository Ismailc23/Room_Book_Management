package com.rest.contoller;

import com.rest.Entity.CustomerEntity;
import com.rest.Entity.LoginUserDto;
import com.rest.Entity.RegisterUserDto;
import com.rest.Entity.User;
import com.rest.Repository.UserRepository;
import com.rest.Response.LoginResponse;
import com.rest.services.AuthenticationService;
import com.rest.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@RequestMapping("/api/auth")
@Controller
public class AuthenticationController {

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String viewLoginPage() {
        return "LoginPage";
    }

    @GetMapping("/signUp")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "SignUpPage";
    }

    @PostMapping("/registrationMethod")
    public String registrationProcess( RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return "redirect:/api/auth/login";
    }

    @PostMapping("/loginMethod")
    public String loginMethod(LoginUserDto loginUserDto, Model model) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return "redirect:/customerForm";
    }
}
