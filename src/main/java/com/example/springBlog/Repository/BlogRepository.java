package com.example.springBlog.Repository;

import com.example.springBlog.Domain.Blog;
import com.example.springBlog.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findAllByUser(User user);
    List<Blog> findByUserId(Long userId);
}
