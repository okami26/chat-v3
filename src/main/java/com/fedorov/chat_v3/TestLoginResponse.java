package com.fedorov.chat_v3;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestLoginResponse {
    @NotEmpty(message = "Name should not be empty")
    private String username;
    @NotEmpty(message = "Name should not be empty")
    private String password;
    @NotEmpty(message = "Name should not be empty")
    private String message;
}

