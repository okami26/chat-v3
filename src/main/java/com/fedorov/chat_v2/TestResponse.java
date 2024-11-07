package com.fedorov.chat_v2;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestResponse {
    @NotEmpty(message = "Name should not be empty")
    private String username;
    @NotEmpty(message = "Name should not be empty")
    private String password;
    @NotEmpty(message = "Name should not be empty")
    private String token;
}
