package hac.ex5.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebController implements WebMvcConfigurer {
    //this works for STATIC pages
    //which means you can add controllers here for those static instead of a seperated one.
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/comments").setViewName("comments");
        registry.addViewController("/about").setViewName("about");
    }

}