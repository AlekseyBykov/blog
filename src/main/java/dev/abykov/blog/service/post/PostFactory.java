package dev.abykov.blog.service.post;

import dev.abykov.blog.domain.Post;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class PostFactory {

    private final DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;

    public Post create(Map<String, Object> meta, String contentMd) {
        Object publishedAt = meta.get("publishedAt");
        LocalDateTime dateTime = null;

        if (publishedAt instanceof Date d) {
            dateTime = LocalDateTime.ofInstant(d.toInstant(), ZoneId.systemDefault());
        } else if (publishedAt instanceof String s) {
            try {
                dateTime = LocalDateTime.parse(s, dtf);
            } catch (Exception e1) {
                try {
                    dateTime = LocalDate.parse(s).atStartOfDay();
                } catch (Exception e2) {
                    System.err.println("Can't parse date: " + s);
                }
            }
        }

        return new Post(
                (String) meta.get("slug"),
                (String) meta.get("title"),
                (String) meta.get("excerpt"),
                (List<String>) meta.getOrDefault("tags", List.of()),
                dateTime,
                Boolean.TRUE.equals(meta.get("published")),
                contentMd
        );
    }
}
