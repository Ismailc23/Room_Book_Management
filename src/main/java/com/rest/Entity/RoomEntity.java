package com.rest.Entity;

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

    @OneToOne(mappedBy = "room")
    private BookingEntity bookings;
}
