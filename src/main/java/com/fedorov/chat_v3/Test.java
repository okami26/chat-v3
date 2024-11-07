package com.fedorov.chat_v3;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Test {
    @NotEmpty(message = "Name should not be empty")
    private String username;
    @NotEmpty(message = "Name should not be empty")
    private String password;
}
