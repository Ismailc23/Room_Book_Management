package com.customer.Entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "room")
public class RoomEntity {

    @Id
    private long roomNumber;

    private int price;

    private String type;

    private boolean available;

    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private BookingsEntity bookings;
}
