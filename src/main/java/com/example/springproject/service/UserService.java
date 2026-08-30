package com.example.springproject.service;

import com.example.springproject.entity.User;
import com.example.springproject.exception.InvalidCredentialsException;
import com.example.springproject.exception.UserAlreadyExistsException;
import com.example.springproject.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

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
    public void registerUser(String name, String email, String rawPassword){
        // User already exists
        if (userRepository.findByEmail(email).isPresent()){
            throw new UserAlreadyExistsException("Email already in use!");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole("ROLE_USER");

        userRepository.save(user);
    }

    public void loginUser(String email, String rawPassword){
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !passwordEncoder.matches(rawPassword, user.getPassword())){
            throw new InvalidCredentialsException("Either password or email incorrect");
        }
    }

    public Optional<User> getUserByEmail (String email){
        return userRepository.findByEmail(email);
    }
}
