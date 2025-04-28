package ru.netology.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostService {
    private final PostRepository repository;

    @Autowired
    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.getAll().values().stream()
                .filter(post -> !post.isRemoved())
                .collect(Collectors.toList());
    }

    public Post getById(long id) {
        return repository.getById(id)
                .orElseThrow(() -> new NotFoundException("Post not found: " + id));
    }

    public Post save(Post post) {
        if (post.getId() != 0 && repository.getById(post.getId()).isEmpty()) {
            throw new NotFoundException("Post not found or removed: " + post.getId());
        }
        return repository.save(post);
    }

    public void removeById(long id) {
        if (repository.getById(id).isEmpty()) {
            throw new NotFoundException("Post not found: " + id);
        }
        repository.removeById(id);
    }
}