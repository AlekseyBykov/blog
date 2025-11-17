package dev.abykov.blog.view;

import dev.abykov.blog.domain.Post;

import java.time.format.DateTimeFormatter;

public class PostViewFactory {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd MMMM yyyy");

    public static PostView from(Post post, String contentHtml) {
        String formattedDate = post.publishedAt() != null
                ? post.publishedAt().format(FORMATTER)
                : "";

        String tags = post.tags() == null
                ? ""
                : String.join(", ", post.tags());

        return new PostView(
                post.title(),
                post.slug(),
                post.excerpt(),
                tags,
                formattedDate,
                contentHtml
        );
    }
}
