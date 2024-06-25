package hac.ex5.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class that implements WebMvcConfigurer for MVC and view controller registration.
 * Used for mapping simple automated controllers pre-configured with response status code and/or a view to render the response body.
 */
@Configuration
public class WebController implements WebMvcConfigurer {
    
    /**
     * Registers several view controllers that provide simple automated controller functionality.
     * This method is particularly useful for mapping the application's static pages that do not require dynamic data.
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/signup").setViewName("signup");
        // This line is commented out to potentially be used for a success page if needed
        // registry.addViewController("/success").setViewName("success");
    }
}
