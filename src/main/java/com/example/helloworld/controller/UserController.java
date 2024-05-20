package com.example.helloworld.controller;

import com.example.helloworld.pojo.Trip;
import com.example.helloworld.pojo.User;
import com.example.helloworld.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User registrationDto) {
        try {
            User user = userService.registerNewUser(
                    registrationDto.getUsername(),
                    registrationDto.getPassword(),
                    registrationDto.getEmail());
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/findAllUsers")
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> users = userService.findAllUsers();

        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/findUser")
    public ResponseEntity<User> findUser(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        Optional<User> user = userService.findByUsername(username);

        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody String userData) {
        JSONObject jsonObj = new JSONObject(userData);
        String username = jsonObj.getString("username");

        User updatedUser = new User();
        updatedUser.setUsername(username);
        updatedUser.setEmail(jsonObj.getString("email"));


        Optional<User> result = userService.updateByName(username, updatedUser);
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        String password = requestBody.get("password");

        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            User foundUser = user.get();
            if (foundUser.getPassword().equals(password)) {
                return new ResponseEntity<>(foundUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestBody String userData) {
        JSONObject jsonObj = new JSONObject(userData);
        String userName = jsonObj.getString("name");
        Optional<Boolean> deletionResult = userService.deleteByName(userName);

        if (deletionResult.isPresent()) {
            Boolean wasDeleted = deletionResult.get();

            String user = wasDeleted.toString();
            return new ResponseEntity<>(user, HttpStatus.OK);

        } else {

            String user = "User not found";
            return new ResponseEntity<>(user, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}