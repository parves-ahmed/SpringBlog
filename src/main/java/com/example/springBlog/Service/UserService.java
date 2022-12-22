package com.example.springBlog.Service;

import com.example.springBlog.Domain.Role;
import com.example.springBlog.Domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    Optional<User> getUserById(Long id);
    User getUserUsername(String Username);
    List<User> getUsers();
}
