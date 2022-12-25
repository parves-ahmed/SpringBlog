package com.example.springBlog.Service;

import com.example.springBlog.DTO.ResponseMessageDTO;
import com.example.springBlog.DTO.UserRequestDTO;
import com.example.springBlog.Domain.UserRequest;

import java.util.List;

public interface UserRequestService {
    ResponseMessageDTO saveRequest(UserRequestDTO userRequestDTO);
    List<UserRequestDTO> getAllUserRequests();
    UserRequestDTO getUserRequestStatus(UserRequestDTO userRequestDTO);
//    ResponseMessageDTO updateUserRequest(UserRequestDTO userRequestDTO);
}
