package com.fedorov.chat_v3.models;

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


    @NotEmpty(message = "Имя пользователя не должно быть пустым")
    @Size(min = 5, max = 20, message = "Имя пользователя должно содержать от 5 до 20 символов")
    @Column(name = "username")
    private String username;

    @NotEmpty(message = "Пароль не должен быть пустым")
    @Size(min = 5, max = 20, message = "Пароль должен содержать от 5 до 20 символов")
    @Column(name = "password")
    private String password;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 5, max = 20, message = "Имя должно содержать от 5 до 20 символов")
    @Column(name = "firstname")
    private String firstname;

    @NotEmpty(message = "Фамилия не должна быть пустой")
    @Size(min = 5, max = 20, message = "Фамилия должна содержать от 5 до 20 символов")
    @Column(name = "lastname")
    private String lastname;

    @NotEmpty(message = "Email не должен быть пустым")
    @Email(message = "Введите корректный email")
    @Column(name = "email")
    private String email;

    @NotEmpty(message = "Телефон не должен быть пустым")
    @Size(min = 5, max = 20, message = "Телефон должен содержать от 5 до 20 символов")
    @Column(name = "phone")
    private String phone;

}
