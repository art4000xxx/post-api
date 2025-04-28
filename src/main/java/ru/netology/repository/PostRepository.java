package ru.netology.repository;

import org.springframework.stereotype.Component;
import ru.netology.model.Post;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class PostRepository {
    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = idCounter.incrementAndGet();
            post.setId(newId);
            posts.put(newId, post);
        } else {
            Post existing = posts.get(post.getId());
            if (existing == null || existing.isRemoved()) {
                throw new IllegalArgumentException("Post with id " + post.getId() + " not found or removed");
            }
            posts.put(post.getId(), post);
        }
        return post;
    }

    public Optional<Post> getById(long id) {
        Post post = posts.get(id);
        if (post != null && !post.isRemoved()) {
            return Optional.of(post);
        }
        return Optional.empty();
    }

    public void removeById(long id) {
        Post post = posts.get(id);
        if (post != null) {
            post.setRemoved(true);
        }
    }

    public ConcurrentHashMap<Long, Post> getAll() {
        return posts;
    }
}