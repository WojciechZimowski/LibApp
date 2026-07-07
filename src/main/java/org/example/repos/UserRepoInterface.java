package org.example.repos;

import org.example.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepoInterface extends JpaRepository<User,String> {
    Optional<User> findByUsername(String userName);
}
