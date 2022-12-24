package com.example.springBlog.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserEntity")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @OneToMany(fetch = LAZY)
    @JsonIgnore
    private Collection<Blog> blogs = new ArrayList<>();
}
