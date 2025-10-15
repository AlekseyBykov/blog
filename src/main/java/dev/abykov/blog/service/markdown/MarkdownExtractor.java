package dev.abykov.blog.service.markdown;

import org.springframework.stereotype.Component;

@Component
public class MarkdownExtractor {

    public record Parts(String frontMatter, String content) {}

    public Parts extract(String text) {
        int start = text.indexOf("---");
        int end = text.indexOf("---", start + 3);
        if (start != 0 || end == -1) {
            throw new IllegalArgumentException("Invalid front matter block");
        }

        String frontMatter = text.substring(start + 3, end).trim();
        String content = text.substring(end + 3).trim();

        return new Parts(frontMatter, content);
    }
}
