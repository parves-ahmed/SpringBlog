package com.example.springBlog.Service;

import com.example.springBlog.DTO.UserDTO;
import com.example.springBlog.Domain.Role;
import com.example.springBlog.Domain.User;
import com.example.springBlog.Exception.CustomException;
import com.example.springBlog.Repository.RoleRepository;
import com.example.springBlog.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;



    @Override
    public User saveUser(User user) {
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setUuid(UUID.randomUUID());
            userRepository.save(user);
        }
        catch (Exception ex){
            log.error(ex.getMessage());
        }
        return user;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(String roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findUsersByUsername(username);
        Role role = roleRepository.findRoleByRoleName(roleName);
//        Role existingRole = roleRepository.findRoleByUserNameAnRoleName()
        user.getRoles().add(role);
    }

    @Override
    public Optional<UserDTO> getUserById(UUID uuid) {
        try{
            Optional<UserDTO> user = Optional.ofNullable(this.mapToUserDto(userRepository.findUserByUuid(uuid)));
            return user;
        }
        catch (Exception ex){
            log.error("User not found in the database");
            throw new RuntimeException(ex.getMessage());
        }

    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUsersByUsername(username);
    }

    @Override
    public List<UserDTO> getUsers() {
        Optional<List<UserDTO>> optionalUserList = Optional.ofNullable(userRepository.findAll().stream().map(this::mapToUserDto).collect(Collectors.toList()));
        List<UserDTO> userList = optionalUserList.orElseThrow(()->new CustomException("No user found"));
        return userList;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        System.out.println(userDTO);
        if(!userDTO.getUuid().isEmpty() && userDTO.getUuid() != null){
            Optional<User> getUser = Optional.ofNullable(userRepository.findUserByUuid(UUID.fromString(userDTO.getUuid())));
            if(getUser.isPresent()){
                User user = new User();
                user.setId(getUser.get().getId());
                user.setUsername(userDTO.getUsername());
                user.setEmail(userDTO.getEmail());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user.setUuid(UUID.randomUUID());
                userRepository.save(user);
                if(user.getId()> 0){
                    userDTO.setUuid(user.getUuid().toString());
                }
            }
        }
        return userDTO;
    }

    @Override
    public Boolean isUserExists(User user) {
        Optional<User> findUser = Optional.ofNullable(userRepository.findUsersByUsername(user.getUsername()));
        if(findUser.isPresent()){
            return true;
        }
        else {
            return false;
        }
    }


    public User mapToModel(UserDTO userDTO){
        User model = new User();
        modelMapper.map(userDTO, model);
        return model;
    }
    public UserDTO mapToUserDto(User user){
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }
}
