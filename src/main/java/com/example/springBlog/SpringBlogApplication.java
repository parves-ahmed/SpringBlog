package com.example.springBlog;

import com.example.springBlog.Domain.Blog;
import com.example.springBlog.Domain.Role;
import com.example.springBlog.Domain.User;
import com.example.springBlog.Domain.UserRequest;
import com.example.springBlog.Service.BlogService;
import com.example.springBlog.Service.UserRequestService;
import com.example.springBlog.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class SpringBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBlogApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService, UserRequestService userRequestService, BlogService blogService){
        return args -> {
            userService.saveUser(new User(null, "parvez",  "parves@gmail.com", "parvez", UUID.randomUUID(), new ArrayList<>(), new ArrayList<>()));
            userService.saveUser(new User(null, "ahmed",  "ahmed@gmail.com",  "ahmed", UUID.randomUUID(),  new ArrayList<>(), new ArrayList<>()));
            userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
            userService.saveRole(new Role(null,"ROLE_USER"));
            userService.saveRole(new Role(null,"ROLE_BLOGGER"));
            userService.addRoleToUser("parvez", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("ahmed", "ROLE_USER");
        };
    }
}
