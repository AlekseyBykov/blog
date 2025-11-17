package dev.abykov.blog.repository;

import dev.abykov.blog.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * In-memory repository for storing and querying blog posts.
 * <p>
 * This repository mimics Spring Data behavior for demo purposes.
 */
@Repository
public class InMemoryPostRepository implements PostRepository {

    private final List<Post> posts = new CopyOnWriteArrayList<>();

    /**
     * Adds a post to the repository.
     */
    @Override
    public void save(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post cannot be null");
        }
        posts.add(post);
    }

    /**
     * Returns all posts sorted by published date (descending).
     */
    public List<Post> findAllSortedByDateDesc() {
        return posts.stream()
                .sorted(
                        Comparator.comparing(
                                Post::publishedAt,
                                Comparator.nullsLast(Comparator.reverseOrder())
                        )
                )
                .toList();
    }

    /**
     * Returns all published posts.
     */
    @Override
    public List<Post> findPublished() {
        return posts.stream()
                .filter(Post::published)
                .sorted(
                        Comparator.comparing(
                                Post::publishedAt,
                                Comparator.nullsLast(Comparator.reverseOrder())
                        )
                )
                .toList();
    }

    /**
     * Finds a post by its slug (only published ones).
     */
    @Override
    public Optional<Post> findBySlug(String slug) {
        if (slug == null || slug.isBlank()) {
            return Optional.empty();
        }

        return posts.stream()
                .filter(p -> slug.equals(p.slug()) && p.published())
                .findFirst();
    }

    @Override
    public List<Post> findAllSorted() {
        return List.of();
    }

    /**
     * Returns total count of posts.
     */
    @Override
    public int count() {
        return posts.size();
    }

    /**
     * Clears the repository (for tests or reloads).
     */
    @Override
    public void clear() {
        posts.clear();
    }
}
