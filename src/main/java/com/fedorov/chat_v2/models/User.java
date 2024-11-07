package com.fedorov.chat_v2.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;


    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, max = 20)
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, max = 20)
    @Column(name="password")
    private String password;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, max = 20)
    @Column(name="firstname")
    private String firstname;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, max = 20)
    @Column(name="lastname")
    private String lastname;

    @NotEmpty(message = "Name should not be empty")
    @Email
    @Column(name="email")
    private String email;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5, max = 20)
    @Column(name="phone")
    private String phone;

}
