package ru.netology.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import ru.netology.model.Post;

@Repository
public class PostRepository {

    private final ConcurrentHashMap<Long, Post> storage = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    public List<Post> all() {
        return new ArrayList<>(storage.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            long newId = counter.incrementAndGet();
            post.setId(newId);
        }
        if (storage.containsKey(post.getId())) {
            storage.replace(post.getId(), post);
        } else {
            storage.put(post.getId(), post);
        }
        return post;
    }

    public void removeById(long id) {
        storage.remove(id);
    }
}
