package hac.ex5.controller;

import hac.ex5.repo.ScamReportRepository;
import hac.ex5.repo.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class HomeController {

    private final ScamReportRepository scamReportRepository;
    private final UserRepository userRepository;

    public HomeController(ScamReportRepository scamReportRepository, UserRepository userRepository) {
        this.scamReportRepository = scamReportRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"/", "/index"})
    public String showIndex(Model model) {

        long totalReports = scamReportRepository.countAllByDateReportedIsTrue();
        long totalUsers = userRepository.countAllByEmailIsTrueAndRoleContaining("USER");
        model.addAttribute("totalReports", totalReports);
        model.addAttribute("totalUsers", totalUsers);
        return "index";  // Ensure this is returning 'index' view
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