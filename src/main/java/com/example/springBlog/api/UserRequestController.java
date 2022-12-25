package com.example.springBlog.api;


import com.example.springBlog.DTO.ResponseMessageDTO;
import com.example.springBlog.DTO.UserRequestDTO;
import com.example.springBlog.Service.UserRequestService;
import com.example.springBlog.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserRequestController {

    private final UserService userService;
    private final UserRequestService userRequestService;

    @GetMapping("/user/getRequestStatus")
    public ResponseEntity<UserRequestDTO> getUserRequests(Principal principal){
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setRequestedUserName(principal.getName());
        return ResponseEntity.ok().body(userRequestService.getUserRequestStatus(userRequestDTO));
    }

    @PostMapping("/user/requestAuthority")
    public ResponseEntity<?> userRequest(@RequestBody UserRequestDTO userRequestDTO, Principal principal){
        log.info("Requested USer: "+ principal.getName());
        userRequestDTO.setRequestedUserName(principal.getName());
        return ResponseEntity.ok().body(userRequestService.saveRequest(userRequestDTO));
    }

    @GetMapping("/admin/getAllUserRequests")
    public ResponseEntity<?>  getAllUserRequests(){
        return ResponseEntity.ok().body(userRequestService.getAllUserRequests());
    }

    @PostMapping("/admin/updateUserRequest")
    public ResponseEntity<?> updateUserRequest(@RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.ok().body(userRequestService.saveRequest(userRequestDTO));
    }
}
