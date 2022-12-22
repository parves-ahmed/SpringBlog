package com.example.springBlog;

import com.example.springBlog.Domain.Role;
import com.example.springBlog.Domain.User;
import com.example.springBlog.Domain.UserRequest;
import com.example.springBlog.Service.UserRequestService;
import com.example.springBlog.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

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
    CommandLineRunner run(UserService userService, UserRequestService userRequestService){
        return args -> {
//            employeeService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
//            employeeService.saveRole(new Role(null,"ROLE_ADMIN"));
//            employeeService.saveRole(new Role(null,"ROLE_MANAGER"));
//            employeeService.saveRole(new Role(null,"ROLE_USER"));
//
//            employeeService.saveEmployee(new Employee(null, "parves", "parves@gmail.com", "parves", new ArrayList<>()));
//            employeeService.saveEmployee(new Employee(null, "ahmed", "ahmed@gmail.com", "ahmed", new ArrayList<>()));
//            employeeService.saveEmployee(new Employee(null, "moon", "moon@gmail.com", "moon", new ArrayList<>()));
//            employeeService.saveEmployee(new Employee(null, "arman", "arman@gmail.com", "arman", new ArrayList<>()));
//
//            employeeService.addRoleToEmployee("parves", "ROLE_SUPER_ADMIN");
//            employeeService.addRoleToEmployee("ahmed", "ROLE_ADMIN");
//            employeeService.addRoleToEmployee("moon", "ROLE_MANAGER");
//            employeeService.addRoleToEmployee("arman", "ROLE_USER");

            userService.saveUser(new User(null, "parvez",  "parves@gmail.com", "01236547896", "parvez", true, "approved",  new ArrayList<>()));
            userService.saveRole(new Role(null,"ROLE_SUPER_ADMIN"));
            userService.addRoleToUser("parvez", "ROLE_SUPER_ADMIN");

            userRequestService.saveRequest(new UserRequest(null, "entered", "authority", userService.getUserUsername("parvez")));
        };
    }
}
