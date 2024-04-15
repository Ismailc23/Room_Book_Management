package com.customer.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;


@Data
@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name = "firstName")
    private String firstName;

    @NotBlank
    @Column(name = "lastName")
    private String lastName;

    @Past
    @Column(name = "dob")
    private Date dob;

    @Email
    @NotBlank
    @Column(name = "email",unique = true)
    private String email;

    @NotBlank
    @Column(name = "username", unique = true)
    private String username;

    @NotBlank
    @Size(min = 8,max = 10)
    @Column(name = "Password")
    private String password;
}

