package com.example.springBlog.Service;

import com.example.springBlog.DTO.UserDTO;
import com.example.springBlog.Domain.Role;
import com.example.springBlog.Domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    Role getRole(String role);
    void addRoleToUser(String username, String roleName);
    Optional<UserDTO> getUserById(UUID uuid);
    User getUserByUsername(String Username);
    List<UserDTO> getUsers();
    UserDTO updateUser(UserDTO userDTO);
    Boolean isUserExists(User user);
}
