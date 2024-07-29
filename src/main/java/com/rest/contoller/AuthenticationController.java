package com.rest.contoller;

import com.rest.Entity.LoginUserDto;
import com.rest.Entity.RegisterUserDto;
import com.rest.Entity.User;
import com.rest.Response.LoginResponse;
import com.rest.services.AuthenticationService;
import com.rest.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RequestMapping("/auth")
@RestController
@CrossOrigin("*")
public class AuthenticationController {
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/registrationMethod")
    public ResponseEntity<User> registrationProcess(@RequestBody RegisterUserDto registerUserDto) {
        System.out.println("Received RegisterUserDto: " + registerUserDto);
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/loginMethod")
    public ResponseEntity<LoginResponse> loginMethod(@RequestBody LoginUserDto loginUserDto, Model model) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        loginResponse.setRoles(authenticatedUser.getRoles()
                .stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList()));
        return ResponseEntity.ok(loginResponse);
    }
}
