package hac.ex5.controller;

import hac.ex5.model.ScamReport;
import hac.ex5.model.User;
import hac.ex5.repo.ScamReportRepository;
import hac.ex5.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;


@Controller
public class ScamReportController {

    private final ScamReportRepository scamReportRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScamReportController(ScamReportRepository scamReportRepository, UserRepository userRepository) {
        this.scamReportRepository = scamReportRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/posts")
    public String showScamReportForm(Model model) {
        model.addAttribute("scamReport", new ScamReport());
        return "user/posts";
    }

    @GetMapping("/feed")
    public String showFeed(Model model) {
        List<ScamReport> scamReports = scamReportRepository.findAllByIdNotNullOrderByDateReported();
        model.addAttribute("scamReports", scamReports);
        return "feed"; // Assuming 'feed.html' is your Thymeleaf template for the feed page
    }


    @PostMapping("/posts")
    public String submitScamReport(@ModelAttribute ScamReport scamReport, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        scamReport.setUser(user);
        scamReport.setDateReported(new Date());
        scamReportRepository.save(scamReport);
        redirectAttributes.addFlashAttribute("message", "Scam report added successfully!");
        return "redirect:/feed";
    }

}