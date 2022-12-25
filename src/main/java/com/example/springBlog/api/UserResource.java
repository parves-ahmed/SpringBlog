package com.example.springBlog.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springBlog.DTO.LoginDTO;
import com.example.springBlog.DTO.ResponseMessageDTO;
import com.example.springBlog.DTO.RoleToUserDTO;
import com.example.springBlog.DTO.UserDTO;
import com.example.springBlog.Domain.Role;
import com.example.springBlog.Domain.User;
import com.example.springBlog.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UserResource {

    private final UserService userService;

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDTO>> getUsers(){
        System.out.println(userService.getUsers());
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("/admin/user/{id}")
    public ResponseEntity<?> getUsers(@PathVariable UUID id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping("/admin/adduser")
    public ResponseEntity<?>saveUser(@RequestBody User user){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        boolean isUserExists = userService.isUserExists(user);
        if(!isUserExists) {
            User savedUser = userService.saveUser(user);
            if(savedUser.getId()>0){
                userService.addRoleToUser(savedUser.getUsername(), "ROLE_SUPER_ADMIN");
            }
            responseMessageDTO.setMessage("user saved successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessageDTO);
        }
        else {
            responseMessageDTO.setMessage("user already exists");
            return ResponseEntity.ok().body(responseMessageDTO);
        }

    }

    @PostMapping("/admin/edituser")
    public ResponseEntity<?>editUser(@RequestBody UserDTO user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/admin/editUser").toUriString());
        return ResponseEntity.created(uri).body(userService.updateUser(user));
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<?>addRoleToUser(@RequestBody RoleToUserDTO roleToUserDTO){
        userService.addRoleToUser(roleToUserDTO.getUsername(), roleToUserDTO.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody LoginDTO loginDTO) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(loginDTO.getRefreshToken());

            String username = decodedJWT.getSubject();
            User user = userService.getUserByUsername(username);
            String access_token = JWT.create()
                    .withSubject(user.getUsername())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 40 * 60 * 1000))
                    .withClaim("roles", user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                    .sign(algorithm);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", access_token);
            return ResponseEntity.ok().body(tokens);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        System.out.println(user);
        boolean isUserExists = userService.isUserExists(user);
        if(!isUserExists){
            User createdUser = userService.saveUser(user);
            if (createdUser.getId() > 0) {
                userService.addRoleToUser(createdUser.getUsername(), "ROLE_USER");
                responseMessageDTO.setMessage("Registration Successful");
                return ResponseEntity.status(HttpStatus.CREATED).body(responseMessageDTO);
            } else {
                responseMessageDTO.setMessage("Registration Failed");
                return ResponseEntity.badRequest().body(responseMessageDTO);
            }
        }
        else {
            responseMessageDTO.setMessage("user already exists");
            return ResponseEntity.ok().body(responseMessageDTO);
        }
    }
}
