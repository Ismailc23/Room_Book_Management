package com.customer.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "roomNo")
    private int roomNo;

    @NotNull
    @Column(name = "price")
    private int price;

    @NotBlank
    @Column(name = "type")
    private String type;

    @NotBlank
    @Column(name = "available")
    private boolean available;
}
