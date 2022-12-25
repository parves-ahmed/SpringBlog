package com.example.springBlog.Repository;

import com.example.springBlog.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUsersByUsername(String username);
    User findUserByUuid(UUID uuid);
}
