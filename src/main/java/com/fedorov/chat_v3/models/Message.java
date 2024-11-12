package com.fedorov.chat_v3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

    @Id
    private int id;

    @NotEmpty(message = "Name should not be empty")
    private String content;

    @NotEmpty(message = "Name should not be empty")
    private String sender;

    @NotEmpty(message = "Name should not be empty")
    private String type;

    @NotEmpty(message = "Name should not be empty")
    private String date;

    @NotEmpty(message = "Name should not be empty")
    private String recipient;


}
