package com.example.springBlog.Repository;

import com.example.springBlog.Domain.User;
import com.example.springBlog.Domain.UserRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest, Long> {
//    List<UserRequest> findUserRequestByRequestType(String requestType);
    UserRequest findUserRequestByRequestedUser(User user);
}
