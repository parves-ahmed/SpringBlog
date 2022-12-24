package com.example.springBlog.api;

import com.example.springBlog.Domain.Blog;
import com.example.springBlog.Domain.User;
import com.example.springBlog.Service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @GetMapping("/blogs")
    public ResponseEntity<List<Blog>> getBlogs(){
        User user = new User();
        user.setId(1L);
        List<Blog> blogs = blogService.getBlogs(user);
        return ResponseEntity.ok().body(blogs);
    }

    @GetMapping("/user/{userId}/blogs")
    public ResponseEntity<List<Blog>> getUserBlogs(@PathVariable Long userId){
        List<Blog> blogs = blogService.getBlogsByUserId(userId);
        return ResponseEntity.ok().body(blogs);
    }

    @PostMapping("/request/blog/approve")
    public ResponseEntity<?> updateBlogStatus(@RequestBody Blog blog){
//        blogService.up
        return null;
    }
}
