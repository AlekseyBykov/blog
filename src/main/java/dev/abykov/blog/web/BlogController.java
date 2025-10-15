package dev.abykov.blog.web;

import dev.abykov.blog.service.markdown.MarkdownService;
import dev.abykov.blog.service.post.PostLoader;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogController {

    private final PostLoader postLoader;
    private final MarkdownService markdownService;

    public BlogController(PostLoader postLoader, MarkdownService markdownService) {
        this.postLoader = postLoader;
        this.markdownService = markdownService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postLoader.findPublished());
        return "index";
    }

    @GetMapping("/post/{slug}")
    public String post(@PathVariable String slug, Model model, HttpServletResponse response) {
        var post = postLoader.findBySlug(slug).orElse(null);
        if (post == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "error/404";
        }

        model.addAttribute("post", post);
        model.addAttribute("contentHtml", markdownService.toHtml(post.contentMd()));
        return "post";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
