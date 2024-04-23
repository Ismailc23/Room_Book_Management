package com.customer.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@Entity
@Table(name = "booking")
public class CustomerRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ReferenceId;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @Column(name = "booked_date")
    private Date bookedDate;

    @Column(name = "end_date")
    private Date endDate;
}
