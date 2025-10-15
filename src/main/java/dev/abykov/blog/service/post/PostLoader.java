package dev.abykov.blog.service.post;

import dev.abykov.blog.domain.Post;
import dev.abykov.blog.service.markdown.FrontMatterParser;
import dev.abykov.blog.service.markdown.MarkdownExtractor;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class PostLoader {

    @Value("${blog.content-path:content/posts}")
    private String contentPath;

    private final FrontMatterParser frontMatterParser;
    private final MarkdownExtractor markdownExtractor;
    private final PostFactory postFactory;

    private final List<Post> posts = new ArrayList<>();

    public PostLoader(
            FrontMatterParser frontMatterParser,
            MarkdownExtractor markdownExtractor,
            PostFactory postFactory
    ) {
        this.frontMatterParser = frontMatterParser;
        this.markdownExtractor = markdownExtractor;
        this.postFactory = postFactory;
    }

    @PostConstruct
    public void loadPosts() throws IOException {
        Path dir = Paths.get(contentPath);
        if (!Files.exists(dir)) {
            return;
        }

        try (var stream = Files.list(dir)) {
            var loaded = stream
                    .filter(f -> f.toString().endsWith(".md"))
                    .map(this::tryLoadPost)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .sorted(Comparator.comparing(Post::publishedAt).reversed())
                    .toList();

            posts.clear();
            posts.addAll(loaded);

            System.out.println("Loaded " + posts.size() + " posts");
        }
    }

    private Optional<Post> tryLoadPost(Path file) {
        try {
            String text = Files.readString(file);
            var parts = markdownExtractor.extract(text);
            Map<String, Object> meta = frontMatterParser.parse(parts.frontMatter());
            Post post = postFactory.create(meta, parts.content());
            return Optional.of(post);
        } catch (Exception e) {
            System.err.println("Error loading " + file.getFileName() + ": " + e.getMessage());
            return Optional.empty();
        }
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
