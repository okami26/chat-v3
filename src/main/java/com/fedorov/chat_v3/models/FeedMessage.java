package com.fedorov.chat_v3.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "feed")
public class FeedMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @NotEmpty(message = "Name should not be empty")
    private String content;

    @NotEmpty(message = "Name should not be empty")
    private String sender;

    @NotEmpty(message = "Name should not be empty")
    private String type;

    @NotEmpty(message = "Name should not be empty")
    private String date;
}