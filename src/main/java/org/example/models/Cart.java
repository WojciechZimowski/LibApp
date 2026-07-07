package org.example.models;

import lombok.*;
import jakarta.persistence.*;
@Entity
@Getter @Setter
@Table(name="carts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name="book_id",nullable = false)
    private Book book;
    @Column(nullable = false)
    private Integer quantity;
}
