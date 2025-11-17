package dev.abykov.blog.repository;

import dev.abykov.blog.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    void save(Post post);

    List<Post> findAllSortedByDateDesc();

    List<Post> findPublished();

    Optional<Post> findBySlug(String slug);

    List<Post> findAllSorted();

    int count();

    void clear();
}
