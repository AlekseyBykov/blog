package dev.abykov.blog.service.post;

import dev.abykov.blog.domain.Post;
import dev.abykov.blog.util.DateParser;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Factory for creating {@link Post} entities from metadata and markdown content.
 */
@Component
public class PostFactory {

    /**
     * Creates a Post object from front matter metadata and markdown content.
     *
     * @param meta      parsed YAML front matter
     * @param contentMd markdown body of the post
     * @return a fully constructed {@link Post}
     */
    public Post create(Map<String, Object> meta, String contentMd) {
        if (meta == null) {
            throw new IllegalArgumentException("Post metadata cannot be null");
        }

        String slug = getAsString(meta, "slug");
        String title = getAsString(meta, "title");
        String excerpt = getAsString(meta, "excerpt");

        List<String> tags = getAsList(meta, "tags");

        LocalDateTime publishedAt = DateParser.parse(meta.get("publishedAt"));
        boolean published = Boolean.TRUE.equals(meta.get("published"));

        return new Post(slug, title, excerpt, tags, publishedAt, published, contentMd);
    }

    private String getAsString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value != null ? value.toString() : null;
    }

    private List<String> getAsList(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value instanceof List<?>) {
            return ((List<?>) value).stream()
                    .map(Object::toString)
                    .toList();
        }
        return List.of();
    }
}
