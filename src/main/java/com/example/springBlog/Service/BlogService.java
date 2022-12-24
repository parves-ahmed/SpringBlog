package com.example.springBlog.Service;

import com.example.springBlog.Domain.Blog;
import com.example.springBlog.Domain.User;

import java.util.List;

public interface BlogService {
    Blog saveBlog(Blog blog);
    List<Blog> getBlogs(User user);
    List<Blog> getBlogsByUserId(Long userId);
}
