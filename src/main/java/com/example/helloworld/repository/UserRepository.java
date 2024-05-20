package com.example.helloworld.repository;

import com.example.helloworld.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByName(String username);
    Optional<User> findByEmail(String email);

}