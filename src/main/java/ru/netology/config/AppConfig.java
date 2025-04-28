package ru.netology.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "ru.netology")
public class AppConfig implements WebMvcConfigurer {
    @Bean
    public PostService postService() {
        return new PostService();
    }

    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }
}