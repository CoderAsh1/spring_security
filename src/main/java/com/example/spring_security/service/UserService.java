package com.example.spring_security.service;

import com.example.spring_security.model.User;
import com.example.spring_security.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager  authManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtService jwtService;

    public User register(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public  String verify(User user){
        try {
            Authentication auth =
                    authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
            if (auth.isAuthenticated()) {
                return jwtService.generateToken(user.getName());
            }
        } catch (Exception e) {
            System.out.println("[DEBUG_LOG] Authentication failed for user: " + user.getName() + " due to: " + e.getMessage());
        }

        return "Failed";
    }

}
