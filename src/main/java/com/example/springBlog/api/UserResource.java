package com.example.springBlog.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.springBlog.DTO.LoginDTO;
import com.example.springBlog.DTO.RoleToUserDTO;
import com.example.springBlog.Domain.Role;
import com.example.springBlog.Domain.User;
import com.example.springBlog.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class UserResource {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers(){
        System.out.println(userService.getUsers());
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User>saveUser(@RequestBody User user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
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
            User user = userService.getUserUsername(username);
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
}
