package dev.abykov.blog.service.post;

import dev.abykov.blog.domain.Post;
import dev.abykov.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Provides business-level operations for working with blog posts.
 */
@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns all published posts, sorted by date descending.
     */
    public List<Post> getPublishedPosts() {
        return repository.findPublished();
    }

    /**
     * Returns a single post by slug (only if published).
     */
    public Optional<Post> getPostBySlug(String slug) {
        return repository.findBySlug(slug);
    }

    /**
     * Returns total number of posts (published or not).
     */
    public int getTotalCount() {
        return repository.count();
    }

    /**
     * For admin or diagnostics — all posts sorted by date.
     */
    public List<Post> getAllSorted() {
        return repository.findAllSortedByDateDesc();
    }
}
