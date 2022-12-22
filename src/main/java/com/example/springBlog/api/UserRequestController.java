package com.example.springBlog.api;


import com.example.springBlog.Domain.UserRequest;
import com.example.springBlog.Service.UserRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserRequestController {

    private final UserRequestService userRequestService;

    @GetMapping("/request/authority")
    public ResponseEntity<List<UserRequest>> getUserRequests(){
        System.out.println(userRequestService.getUserRequestByRequestType("authority"));
        return ResponseEntity.ok().body(userRequestService.getUserRequestByRequestType("authority"));
    }

    @PostMapping("/request/authority/response")
    public ResponseEntity<UserRequest> responseForAuthority(@RequestBody UserRequest userRequest){
        return ResponseEntity.ok().body(userRequestService.saveRequest(userRequest));
    }
}
