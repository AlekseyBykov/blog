package dev.abykov.blog.domain;

import java.time.LocalDateTime;
import java.util.List;

public record Post(
        String slug,
        String title,
        String excerpt,
        List<String> tags,
        LocalDateTime publishedAt,
        boolean published,
        String contentMd
) {}
