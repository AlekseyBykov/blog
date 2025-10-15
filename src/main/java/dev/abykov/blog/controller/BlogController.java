package dev.abykov.blog.controller;

import dev.abykov.blog.service.markdown.MarkdownService;
import dev.abykov.blog.service.post.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BlogController {

    private final PostService postService;
    private final MarkdownService markdownService;

    public BlogController(
            PostService postService,
            MarkdownService markdownService
    ) {
        this.postService = postService;
        this.markdownService = markdownService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("posts", postService.getPublishedPosts());
        return "index";
    }

    @GetMapping("/post/{slug}")
    public String post(@PathVariable String slug, Model model) {
        var postOpt = postService.getPostBySlug(slug);
        if (postOpt.isEmpty()) {
            return "404";
        }

        var post = postOpt.get();
        model.addAttribute("post", post);
        model.addAttribute("contentHtml", markdownService.toHtml(post.contentMd()));
        return "post";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
