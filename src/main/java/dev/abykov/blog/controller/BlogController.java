package dev.abykov.blog.controller;

import dev.abykov.blog.domain.Post;
import dev.abykov.blog.service.markdown.MarkdownService;
import dev.abykov.blog.service.post.PostService;
import dev.abykov.blog.view.PostView;
import dev.abykov.blog.view.PostViewFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        List<Post> posts = postService.getPublishedPosts();
        model.addAttribute("posts", posts);
        return "index";
    }

    @GetMapping("/post/{slug}")
    public String post(@PathVariable String slug, Model model) {
        Post post = postService.getPostBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String html = markdownService.toHtml(post.contentMd());
        PostView view = PostViewFactory.from(post, html);

        model.addAttribute("post", view);

        return "post";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
