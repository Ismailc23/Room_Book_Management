package com.rest.services;

import com.rest.Entity.*;
import com.rest.ExceptionHandling.UserException.InvalidCredentialsException;
import com.rest.ExceptionHandling.UserException.UserNameAlreadyExistException;
import com.rest.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rest.Repository.RoleRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User signup(RegisterUserDto input) {
        if (userRepository.existsByEmail(input.getEmail())) {
            throw new UserNameAlreadyExistException("Username already exists: " + input.getEmail());
        }
        User user = new User();
                user.setFullName(input.getFullName());
                user.setEmail(input.getEmail());
                user.setPassword(passwordEncoder.encode(input.getPassword()));
                Optional<Role> userRole = roleRepository.findByName(RoleEnum.USER);
                Set<Role> roles = new HashSet<>();
                userRole.ifPresent(roles::add);
                user.setRoles(roles);
        return userRepository.save(user);
    }

    public User authenticate(LoginUserDto input) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()
                    )
            );
        }
        catch (AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));
    }
}