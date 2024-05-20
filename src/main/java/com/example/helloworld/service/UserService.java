package com.example.helloworld.service;

import com.example.helloworld.pojo.Trip;
import com.example.helloworld.pojo.User;
import com.example.helloworld.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private String password;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User registerNewUser(String username, String password, String email) {

        Optional<User> existingUserByUsername = userRepository.findByName(username);
        Optional<User> existingUserByEmail = userRepository.findByEmail(email);

        if (existingUserByUsername.isPresent() || existingUserByEmail.isPresent()) {
            throw new IllegalStateException("Username or Email already exists.");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        return userRepository.save(newUser);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByName(username);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> updateByName(String name, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findByName(name);

        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());

            return Optional.of(userRepository.save(existingUser));
        } else {
            return Optional.empty();
        }
    }

    public Optional deleteByName(String name) {
        Optional<User> existingUserOpt = userRepository.findByName(name);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            userRepository.delete(existingUser);
            return Optional.of(true);
        } else {
            return Optional.empty();
        }
    }



}

