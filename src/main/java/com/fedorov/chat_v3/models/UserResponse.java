package com.fedorov.chat_v3.models;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    @NotEmpty(message = "Name should not be empty")
    private int id;
    @NotEmpty(message = "Name should not be empty")
    private String username;
    @NotEmpty(message = "Name should not be empty")
    private String password;
    @NotEmpty(message = "Name should not be empty")
    private String token;
}