package dev.abykov.blog.view;

public record PostView(
        String title,
        String slug,
        String excerpt,
        String tags,
        String publishedAt,
        String contentHtml
) {}
