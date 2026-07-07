package org.example.services.impl;

import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.models.User;
import org.example.repos.UserRepoInterface;
import org.example.services.AuthServiceInterface;
import org.example.services.UserServiceInterface;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AuthService implements AuthServiceInterface {
    private final UserRepoInterface userRepo;

    public AuthService(UserRepoInterface userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean register(String login, String rawPassword) {
        if (userRepo.findByUsername(login).isPresent()) {
            return false;
        }

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username(login)
                .password(BCrypt.hashpw(rawPassword, BCrypt.gensalt()))
                .role("USER")
                .build();

        userRepo.save(user);
        return true;
    }

    @Override
    public Optional<User> login(String login, String rawPassword) {
        String hashed = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        return userRepo.findByUsername(login)
                .filter(u -> BCrypt.checkpw(rawPassword, u.getPassword()));
    }
}
