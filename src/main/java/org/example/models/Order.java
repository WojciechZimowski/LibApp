package org.example.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;
    @Column(name="total_price",nullable = false)
    private Double totalPrice;
    @Column(nullable = false)
    private String status;
    @Column(name="items_json",columnDefinition = "TEXT",nullable = false)
    private String itemJson;


}
