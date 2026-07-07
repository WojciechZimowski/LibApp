package org.example.services.impl;

import org.example.models.User;
import org.example.repos.UserRepoInterface;
import org.example.services.UserServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserServiceInterface {
    private final UserRepoInterface userRepo;


    public UserService(UserRepoInterface userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findUserByName(String name) {
        return userRepo.findByUsername(name);
    }

    @Override
    public Optional<User> findUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public void deleteUser(String id) {
        userRepo.deleteById(id);
    }
}
