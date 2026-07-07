package org.example.repos;

import org.example.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepoInterface extends JpaRepository<Cart,String> {
    List<Cart> findByUser_Id(String userId);
    void deleteByUserId(String userId);
}
