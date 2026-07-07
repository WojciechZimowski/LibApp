package org.example.services;

import org.example.models.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInterface {
    List<User> findAllUsers();
    Optional<User> findUserByName(String name);
    Optional<User> findUserById(String id);
    void deleteUser(String id);
}
