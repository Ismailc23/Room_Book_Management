package com.customer.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "booking")
public class BookingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long referenceId;

    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    @JsonIgnore
    private CustomerEntity customer;

    @OneToOne
    @JoinColumn(name = "room_number",nullable = false)
    @JsonIgnore
    private RoomEntity room;

    private String customerFirstName;

    private String customerLastName;

    private String roomType;

    private int roomPrice;

    @Column(name = "booked_date")
    private Date bookedDate;

    private Date stayStartDate;

    private Date stayEndDate;

    @Column(name = "end_date")
    private Date endDate;
}
