package com.customer.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long RoomId;

    private int roomNumber;

    private int price;

    private String type;

    private boolean available;
}
