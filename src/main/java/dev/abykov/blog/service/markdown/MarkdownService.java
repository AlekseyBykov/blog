package dev.abykov.blog.service.markdown;

import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.misc.Extension;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Converts Markdown text into HTML using Flexmark.
 */
@Service
public class MarkdownService {

    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownService() {
        List<Extension> extensions = Arrays.asList(
                TablesExtension.create(),
                AutolinkExtension.create(),
                StrikethroughExtension.create()
        );

        this.parser = Parser.builder()
                .extensions(extensions)
                .build();

        this.renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();
    }

    /**
     * Converts a Markdown string into HTML.
     *
     * @param markdown markdown text
     * @return HTML representation, or an empty string if input is null/blank
     */
    public String toHtml(String markdown) {
        if (markdown == null || markdown.isBlank()) {
            return "";
        }

        try {
            Document document = parser.parse(markdown);
            return renderer.render(document);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to render Markdown: " + e.getMessage(), e);
        }
    }
}
