package com.rest.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@Entity
@Table(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long referenceId;

    @ManyToOne
    @JoinColumn(name = "customer_Id",nullable = false)
    @JsonIgnore
    private CustomerEntity customer;

    private String customerFirstName;

    private String customerLastName;

    private String roomType;

    private int roomPrice;

    @OneToOne
    @JoinColumn(name = "room_Number",nullable = false)
    @JsonIgnore
    private RoomEntity room;

    @Column(name = "booked_date")
    private Date bookedDate;

    private Date stayStartDate;

    private Date stayEndDate;
}
