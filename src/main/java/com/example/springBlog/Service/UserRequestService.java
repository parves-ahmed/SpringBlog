package com.example.springBlog.Service;

import com.example.springBlog.DTO.UserRequestDTO;
import com.example.springBlog.Domain.UserRequest;

import java.util.List;

public interface UserRequestService {
    UserRequest saveRequest(UserRequestDTO userRequestDTO);
    List<UserRequest>  getUserRequestByRequestType(String requestType);
    UserRequestDTO getUserRequestStatus(UserRequestDTO userRequestDTO);
}
