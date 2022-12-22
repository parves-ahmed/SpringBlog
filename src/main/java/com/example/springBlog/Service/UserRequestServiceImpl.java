package com.example.springBlog.Service;

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

    private final UserRequestRepository userRequestRepository;

    @Override
    public UserRequest saveRequest(UserRequest userRequest) {
        return userRequestRepository.save(userRequest);
    }

    @Override
    public List<UserRequest> getUserRequestByRequestType(String requestType) {
        Optional<List<UserRequest>> requestsOptional = Optional.ofNullable(userRequestRepository.findUserRequestByRequestType(requestType));
        List<UserRequest> allRequests = requestsOptional.orElseThrow(()->new CustomException("No user Found Exception"));
        return allRequests;
    }
}
