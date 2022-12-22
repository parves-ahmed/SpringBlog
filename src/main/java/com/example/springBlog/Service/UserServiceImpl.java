package com.example.springBlog.Service;

import com.example.springBlog.Domain.Role;
import com.example.springBlog.Domain.User;
import com.example.springBlog.Repository.RoleRepository;
import com.example.springBlog.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findUsersByUsername(username);
        Role role = roleRepository.findRoleByRoleName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        try{
            Optional<User> user = userRepository.findById(id);
            return user;
        }
        catch (Exception ex){
            log.error("User not found in the database");
            throw new RuntimeException(ex.getMessage());
        }

    }

    @Override
    public User getUserUsername(String username) {
        return userRepository.findUsersByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}