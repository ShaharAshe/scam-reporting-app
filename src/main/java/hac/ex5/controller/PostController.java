package hac.ex5.controller;

import hac.ex5.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public String showPosts(Model model) {
        model.addAttribute("posts", postService.getPosts());
        return "posts";  // points to posts.html Thymeleaf template
    }
}