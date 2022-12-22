package com.example.springBlog.Service;

import com.example.springBlog.Domain.UserRequest;

import java.util.List;

public interface UserRequestService {
    UserRequest saveRequest(UserRequest userRequest);
    List<UserRequest>  getUserRequestByRequestType(String requestType);
}
