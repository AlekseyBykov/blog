package dev.abykov.blog.service.post;

import dev.abykov.blog.domain.Post;
import dev.abykov.blog.repository.InMemoryPostRepository;
import dev.abykov.blog.repository.PostRepository;
import dev.abykov.blog.service.markdown.FrontMatterParser;
import dev.abykov.blog.service.markdown.MarkdownExtractor;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Loads Markdown posts from the file system and saves them into {@link InMemoryPostRepository}.
 */
@Service
public class PostLoader {

    private static final Logger log = LoggerFactory.getLogger(PostLoader.class);

    @Value("${blog.content-path:content/posts}")
    private String contentPath;

    private final FrontMatterParser frontMatterParser;
    private final MarkdownExtractor markdownExtractor;
    private final PostFactory postFactory;
    private final PostRepository postRepository;

    public PostLoader(
            FrontMatterParser frontMatterParser,
            MarkdownExtractor markdownExtractor,
            PostFactory postFactory,
            PostRepository postRepository
    ) {
        this.frontMatterParser = frontMatterParser;
        this.markdownExtractor = markdownExtractor;
        this.postFactory = postFactory;
        this.postRepository = postRepository;
    }

    @PostConstruct
    public void loadPosts() throws IOException {
        Path dir = Paths.get(contentPath);
        if (!Files.exists(dir)) {
            log.warn("Content directory not found: {}", dir.toAbsolutePath());
            return;
        }

        try (Stream<Path> filePaths = Files.list(dir)) {
            List<Post> loadedPosts = filePaths
                    .filter(filePath -> filePath.toString().endsWith(".md"))
                    .map(this::tryLoadPost)
                    .flatMap(Optional::stream)
                    .toList();

            postRepository.clear();
            loadedPosts.forEach(postRepository::save);

            log.info(
                    "Loaded {} posts from {}",
                    loadedPosts.size(),
                    dir.toAbsolutePath()
            );
        }
    }

    private Optional<Post> tryLoadPost(Path filePath) {
        try {
            String markdownContent = Files.readString(filePath);
            MarkdownExtractor.Parts parts = markdownExtractor.extract(markdownContent);

            Map<String, Object> meta = frontMatterParser.parse(parts.frontMatter());
            Post post = postFactory.create(meta, parts.content());
            return Optional.of(post);
        } catch (Exception e) {
            log.error("Error loading {}: {}", filePath.getFileName(), e.getMessage());
            return Optional.empty();
        }
    }
}
