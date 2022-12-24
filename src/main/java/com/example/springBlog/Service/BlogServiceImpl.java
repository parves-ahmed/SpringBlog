package com.example.springBlog.Service;

import com.example.springBlog.Domain.Blog;
import com.example.springBlog.Domain.User;
import com.example.springBlog.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogServiceImpl implements BlogService{

    private final BlogRepository blogRepository;

    @Override
    public Blog saveBlog(Blog blog)  {
        return blogRepository.save(blog);
    }

    @Override
    public List<Blog> getBlogs(User user) {
        return blogRepository.findAllByUser(user);
    }

    @Override
    public List<Blog> getBlogsByUserId(Long userId) {
        return blogRepository.findByUserId(userId);
    }
}
