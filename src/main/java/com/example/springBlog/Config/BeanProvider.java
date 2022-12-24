package com.example.springBlog.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanProvider {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
