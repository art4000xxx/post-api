package ru.netology.repository;

import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<Post> all() {
        return new ArrayList<>(posts.values()).stream()
                .filter(post -> !post.isRemoved())
                .toList();
    }

    public Optional<Post> getById(long id) {
        Post post = posts.get(id);
        if (post == null || post.isRemoved()) {
            return Optional.empty();
        }
        return Optional.of(post);
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = idCounter.incrementAndGet();
            post.setId(newId);
            posts.put(newId, post);
        } else {
            Post existingPost = posts.get(post.getId());
            if (existingPost == null || existingPost.isRemoved()) {
                throw new NotFoundException("Post with id " + post.getId() + " not found or removed");
            }
            posts.put(post.getId(), post);
        }
        return post;
    }

    public void removeById(long id) {
        Post post = posts.get(id);
        if (post == null || post.isRemoved()) {
            throw new NotFoundException("Post with id " + id + " not found or already removed");
        }
        post.setRemoved(true);
    }
}