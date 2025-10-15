package dev.abykov.blog.service.markdown;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MarkdownService {

    private final Parser parser = Parser.builder()
            .extensions(List.of(TablesExtension.create()))
            .build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    public String toHtml(String md) {
        if (md == null || md.isBlank()) {
            return "";
        }
        return renderer.render(parser.parse(md));
    }
}
