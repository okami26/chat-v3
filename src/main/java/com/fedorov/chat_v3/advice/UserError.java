package com.fedorov.chat_v3.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserError {

    private String message;
    private int status;

}
