package hac.ex5.controller;

import hac.ex5.repo.ScamReportRepository;
import hac.ex5.repo.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Controller for handling the main homepage and other general routes.
 */
@Controller
public class HomeController {

    @Autowired
    private ScamReportRepository scamReportRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Displays the main index page and manages user visit cookies.
     * 
     * @param model The model for the view template.
     * @param response The HttpServletResponse for managing cookies.
     * @param visitTime The time of the last visit, retrieved from cookies.
     * @return The name of the index view template.
     */
    @GetMapping({"/", "/index"})
    public String showIndex(Model model, HttpServletResponse response, @CookieValue(value = "visitTime", defaultValue = "N/A") String visitTime) {
        LocalDateTime now = LocalDateTime.now();
        String currentTime = now.toString();

        Cookie cookie = new Cookie("visitTime", currentTime);
        cookie.setMaxAge(7 * 24 * 60 * 60); // Set cookie expiry for 7 days
        response.addCookie(cookie);

        long totalReports = scamReportRepository.countAllByDateReportedIsTrue();
        long totalUsers = userRepository.countAllByEmailIsTrueAndRoleContaining("USER");
        model.addAttribute("totalReports", totalReports);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("visitTime", visitTime);
        return "index";
    }

    /**
     * Handles redirection to the home page upon a successful action.
     * 
     * @param model The model for the view template.
     * @return Redirection to the home page.
     */
    @GetMapping({"/success"})
    public String showSuccess(Model model) {
        return "redirect:/";
    }

    /**
     * Global exception handler that directs to a custom error page.
     * 
     * @param ex The exception that was thrown.
     * @param model The model to add the error message to.
     * @return The name of the error view template.
     */
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        String errorMessage = (ex != null ? ex.getMessage() : "Unknown error");
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}
