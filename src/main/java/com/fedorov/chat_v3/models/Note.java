package com.fedorov.chat_v3.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Note {
    private String content;

    private String sender;

    private String date;
    private String type;

}
