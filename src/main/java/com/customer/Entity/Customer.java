package com.customer.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "firstName")
    @NotNull
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name ="dob")
    private Date dob;


    @Column(name = "email")
    private String email;

    @Column(name = "username", unique = true)
    private String username;;

    @Column(name = "Password")
    private String password;

    public Customer(){
    }

    public Customer(String firstName, String lastName, Date dob, String email, String username, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
