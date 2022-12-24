package com.example.springBlog.Service;

import com.example.springBlog.DTO.UserRequestDTO;
import com.example.springBlog.Domain.User;
import com.example.springBlog.Domain.UserRequest;
import com.example.springBlog.Exception.CustomException;
import com.example.springBlog.Repository.UserRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserRequestServiceImpl implements UserRequestService{

    private final UserService userService;
    private final UserRequestRepository userRequestRepository;

    @Override
    public UserRequest saveRequest(UserRequestDTO userRequestDTO) {
        User user = userService.getUserByUsername(userRequestDTO.getRequestedUserName());
        Optional<UserRequest> getUserRequest = Optional.ofNullable(userRequestRepository.findUserRequestByRequestedUser(user));
        UserRequest userRequest = new UserRequest();
        if(!getUserRequest.isPresent()){
            userRequest.setRequestedUser(user);
            userRequest.setRequestStatus(userRequestDTO.getRequestStatus());
            userRequestRepository.save(userRequest);
        }
        return userRequest;
    }

    @Override
    public List<UserRequest> getUserRequestByRequestType(String requestType) {
//        Optional<List<UserRequest>> requestsOptional = Optional.ofNullable(userRequestRepository.findUserRequestByRequestType(requestType));
//        List<UserRequest> allRequests = requestsOptional.orElseThrow(()->new CustomException("No user Found Exception"));
        return null;
    }

    @Override
    public UserRequestDTO getUserRequestStatus(UserRequestDTO userRequestDTO) {
        Optional<User> user = Optional.ofNullable(userService.getUserByUsername(userRequestDTO.getRequestedUserName()));
        if(user.isPresent()){
            try{
                Optional<UserRequest> userRequest = Optional.ofNullable(userRequestRepository.findUserRequestByRequestedUser(user.get()));
                userRequestDTO.setRequestStatus(userRequest.isPresent() ? userRequest.get().getRequestStatus() : "");
            }
            catch (Exception ex){
                log.error(ex.getMessage());
                userRequestDTO.setRequestStatus("");
            }

        }
        return userRequestDTO;
    }
}
