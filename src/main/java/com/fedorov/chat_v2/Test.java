package com.fedorov.chat_v2;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.lang.NonNull;

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
