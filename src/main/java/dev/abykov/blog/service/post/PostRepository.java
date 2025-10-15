package dev.abykov.blog.service.post;

import dev.abykov.blog.domain.Post;

import java.util.*;

public class PostRepository {

    private final List<Post> posts = new ArrayList<>();

    public void add(Post post) {
        posts.add(post);
    }

    public void sortByDateDesc() {
        posts.sort(Comparator.comparing(Post::publishedAt).reversed());
    }

    public int count() {
        return posts.size();
    }

    public List<Post> findPublished() {
        return posts.stream().filter(Post::published).toList();
    }

    public Optional<Post> findBySlug(String slug) {
        return posts.stream()
                .filter(p -> p.slug().equals(slug) && p.published())
                .findFirst();
    }
}
