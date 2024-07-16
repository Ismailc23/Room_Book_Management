package com.rest.services;

import com.rest.Entity.Role;
import com.rest.Entity.RoleEnum;
import com.rest.Entity.User;
import com.rest.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.rest.Repository.RoleRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createAdminUser(String fullName, String email, String password) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return existingUser.get();
        }
        User adminUser = new User();
        adminUser.setFullName(fullName);
        adminUser.setEmail(email);
        adminUser.setPassword(passwordEncoder.encode(password));
        Optional<Role> adminRole = roleRepository.findByName(RoleEnum.ADMIN);
        Set<Role> roles = new HashSet<>();
        adminRole.ifPresent(roles::add);
        adminUser.setRoles(roles);
        return userRepository.save(adminUser);
    }
}
