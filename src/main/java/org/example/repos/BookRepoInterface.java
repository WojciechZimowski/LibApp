package org.example.repos;

import org.example.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepoInterface extends JpaRepository<Book,String> {

}
