package com.example.springBlog.Service;

import com.example.springBlog.DTO.ResponseMessageDTO;
import com.example.springBlog.DTO.UserRequestDTO;
import com.example.springBlog.Domain.User;
import com.example.springBlog.Domain.UserRequest;
import com.example.springBlog.Exception.CustomException;
import com.example.springBlog.Repository.UserRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserRequestServiceImpl implements UserRequestService{

    private final UserService userService;
    private final UserRequestRepository userRequestRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseMessageDTO saveRequest(UserRequestDTO userRequestDTO) {
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        Optional<User> user = Optional.ofNullable(userService.getUserByUsername(userRequestDTO.getRequestedUserName()));                   //find user
        if (user.isPresent()) {
            Optional<UserRequest> getUserRequest = Optional.ofNullable(userRequestRepository.findUserRequestByRequestedUser(user.get()));  //find user request
            if (!getUserRequest.isPresent()) {
                saveUserRequest(null, user.get(), userRequestDTO.getRequestStatus());
            } else {
                UserRequest updatedRequest = saveUserRequest(getUserRequest.get().getId(), user.get(), userRequestDTO.getRequestStatus());
                if(updatedRequest.getId() > 0 && updatedRequest.getRequestStatus().equals("approved")){
                    userService.addRoleToUser(updatedRequest.getRequestedUser().getUsername(), "ROLE_BLOGGER");                   // set role to ROLE_BLOGGER
                }
                else {
                    log.info("User role is not updated to blogger");
                }
            }
        }
        else {
            responseMessageDTO.setMessage("User doesn't exists");
        }
        return responseMessageDTO;
    }

    private UserRequest saveUserRequest(Long requestId, User requestedUser, String requestStatus){
        UserRequest userRequest = new UserRequest();
        try{
            userRequest.setId(requestId);
            userRequest.setRequestedUser(requestedUser);
            userRequest.setRequestStatus(requestStatus);
            UserRequest request = userRequestRepository.save(userRequest);
            String msg = request.getId() > 0 ? "User request saved successfully" : "Some went wrong";
            log.info(msg);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
        }
        return userRequest;
    }

    @Override
    public List<UserRequestDTO> getAllUserRequests() {
        Optional<List<UserRequestDTO>> requestsOptional = Optional.ofNullable(userRequestRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList()));
        List<UserRequestDTO> allRequests = requestsOptional.orElseThrow(()->new CustomException("No user Found Exception"));
        return allRequests;
    }

    @Override
    public UserRequestDTO getUserRequestStatus(UserRequestDTO userRequestDTO) {
        User user = userService.getUserByUsername(userRequestDTO.getRequestedUserName());
        try {
            Optional<UserRequest> userRequest = Optional.ofNullable(userRequestRepository.findUserRequestByRequestedUser(user));
            userRequestDTO.setRequestStatus(userRequest.isPresent() ? userRequest.get().getRequestStatus() : "");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            userRequestDTO.setRequestStatus("");
        }
        return userRequestDTO;
    }

    public UserRequest mapToModel(UserRequestDTO userRequestDTO){
        UserRequest userRequest = new UserRequest();
        modelMapper.map(userRequestDTO, userRequest);
        return userRequest;
    }

    public UserRequestDTO mapToDto(UserRequest userRequest){
        UserRequestDTO userRequestDTO = modelMapper.map(userRequest, UserRequestDTO.class);
        return userRequestDTO;
    }
}
