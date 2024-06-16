package hac.ex5.controller;

import hac.ex5.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final PostService postService;

    @Autowired
    public HomeController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping({"/", "/index"})
    public String showIndex(Model model) {
        model.addAttribute("posts", postService.getPosts());
        return "index";  // Ensure this is returning 'index' view
    }
}