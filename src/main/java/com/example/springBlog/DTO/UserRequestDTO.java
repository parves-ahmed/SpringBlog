package com.example.springBlog.DTO;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String requestStatus;
    private String requestedUserName;
}
