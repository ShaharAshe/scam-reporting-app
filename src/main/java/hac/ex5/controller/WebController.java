package hac.ex5.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebController implements WebMvcConfigurer {
    //this works for STATIC pages
    //which means you can add controllers here for those static instead of a seperated one.
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/feed").setViewName("feed");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/success").setViewName("success");
        registry.addViewController("/posts").setViewName("admin/posts");
        registry.addViewController("/admin").setViewName("admin/admin");
    }

}