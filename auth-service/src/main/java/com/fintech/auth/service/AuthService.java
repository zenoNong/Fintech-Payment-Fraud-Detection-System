package com.fintech.auth.service;

import com.fintech.auth.model.UserCredential;
import com.fintech.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;

    public String saveUser(UserCredential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "User added to system";
    }

    public String generateToken(String username) {
        UserCredential user = repository.findByUsername(username).orElseThrow();
        return jwtService.generateToken(username, user.getRole());
    }
}
