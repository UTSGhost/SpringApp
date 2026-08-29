package com.example.springproject.service;

import com.example.springproject.entity.User;
import com.example.springproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    /**
     * Encodes password and registers users using userRepository
     * @param name
     * @param email
     * @param rawPassword
     */
    public boolean registerUser(String name, String email, String rawPassword){
        // User already exists
        if (userRepository.findByEmail(email).isPresent()){
            return false;
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        user.setPassword(encodedPassword);

        userRepository.save(user);
        return true;
    }

    public boolean loginUser(String email, String rawPassword){
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()){
            return false;
        }
        User user = userOpt.get();
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public Optional<User> getUserByEmail (String email){
        return userRepository.findByEmail(email);
    }
}
