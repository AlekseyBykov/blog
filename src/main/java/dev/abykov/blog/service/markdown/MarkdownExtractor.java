package dev.abykov.blog.service.markdown;

import org.springframework.stereotype.Component;

@Component
public class MarkdownExtractor {

    private static final String FRONT_MATTER_DELIMITER = "---";

    /**
     * Represents a Markdown file split into YAML front matter and Markdown content.
     */
    public record Parts(String frontMatter, String content) {
    }

    /**
     * Extracts YAML front matter and Markdown content from a text.
     * <p>
     * Expected format:
     * ---
     * key: value
     * ---
     * # Markdown content...
     *
     * @param text raw Markdown file content
     * @return separated front matter and content parts
     * @throws IllegalArgumentException if no valid front matter block is found
     */
    public Parts extract(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Input text is null or empty");
        }

        int firstDelimiter = text.indexOf(FRONT_MATTER_DELIMITER);
        if (firstDelimiter != 0) {
            throw new IllegalArgumentException("Front matter must start at the beginning of the file");
        }

        int secondDelimiter = text.indexOf(FRONT_MATTER_DELIMITER, FRONT_MATTER_DELIMITER.length());
        if (secondDelimiter == -1) {
            throw new IllegalArgumentException("Missing closing front matter delimiter");
        }

        String frontMatter = text.substring(firstDelimiter + FRONT_MATTER_DELIMITER.length(), secondDelimiter).trim();
        String content = text.substring(secondDelimiter + FRONT_MATTER_DELIMITER.length()).trim();

        return new Parts(frontMatter, content);
    }
}
