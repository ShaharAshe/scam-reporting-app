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

@Controller
public class HomeController {

    private final ScamReportRepository scamReportRepository;
    private final UserRepository userRepository;

    public HomeController(ScamReportRepository scamReportRepository, UserRepository userRepository) {
        this.scamReportRepository = scamReportRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"/", "/index"})
    public String showIndex(Model model, HttpServletResponse response, @CookieValue(value = "visitTime", defaultValue = "N/A") String visitTime) {
        System.out.println("cookies last: "+visitTime);
        LocalDateTime now = LocalDateTime.now();
        String currentTime = now.toString();
        System.out.println("cookies new: "+currentTime);

        Cookie cookie = new Cookie("visitTime", currentTime);
        System.out.println("cookies: "+visitTime);

        cookie.setMaxAge(7 * 24 * 60 * 60);
        System.out.println("cookies: "+visitTime);

        response.addCookie(cookie);
        System.out.println("cookies: "+visitTime);


        long totalReports = scamReportRepository.countAllByDateReportedIsTrue();
        long totalUsers = userRepository.countAllByEmailIsTrueAndRoleContaining("USER");
        model.addAttribute("totalReports", totalReports);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("visitTime", visitTime);
        return "index";
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {

        //logger.error("Exception during execution of SpringSecurity application", ex);
        String errorMessage = (ex != null ? ex.getMessage() : "Unknown error");

        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }
}