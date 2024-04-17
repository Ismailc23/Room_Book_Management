package com.customer.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int roomNumber;

    @Column(name = "price")
    private int price;

    @Column(name = "type")
    private String type;

    @Column(name = "available")
    private boolean available;

    @ManyToOne
    private CustomerEntity customer;
}