package com.example.spring_security.service;

import com.example.spring_security.model.User;
import com.example.spring_security.model.UserPrinciple;
import com.example.spring_security.repo.UserRepo;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@NullMarked
public class SUserDetailsService implements UserDetailsService {
    private UserRepo userRepo;

    public SUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepo.findByName(username);
       if (user == null) {
           System.out.println("[DEBUG_LOG] User not found: " + username);
           throw new UsernameNotFoundException("user not found");
       }
       return new UserPrinciple(user);

    }
}
