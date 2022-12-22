package ru.job4j.dreamjob.store;

import ru.job4j.dreamjob.model.Post;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostStore {

    private static final PostStore INST = new PostStore();

    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private static int count = 3;

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "No  experience", LocalDateTime.now()));
        posts.put(2, new Post(2, "Middle Java Job", "Experience 1-3 years", LocalDateTime.now()));
        posts.put(3, new Post(3, "Senior Java Job", "Experience 3+ years", LocalDateTime.now()));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public void add(Post post) {
        post.setId(++count);
        posts.put(count, post);
    }
}