package hac.ex5.controller;

import hac.ex5.model.ScamReport;
import hac.ex5.model.User;
import hac.ex5.repo.UserRepository;
import hac.ex5.service.ScamReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/scam-reports")
public class ScamReportController {

    private final ScamReportService scamReportService;
    private final UserRepository userRepository;

    @Autowired
    public ScamReportController(ScamReportService scamReportService, UserRepository userRepository) {
        this.scamReportService = scamReportService;
        this.userRepository = userRepository;
    }

    @GetMapping("/new")
    public String showScamReportForm(Model model) {
        model.addAttribute("scamReport", new ScamReport());
        return "scam-reports/create";
    }

    @GetMapping("/feed")
    public String showFeed(Model model) {
        List<ScamReport> scamReports = scamReportService.getAllReportsOrdered();
        model.addAttribute("scamReports", scamReports);
        return "scam-reports/feed";
    }

    @GetMapping("/manage")
    public String showUserPosts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<ScamReport> scamReports = scamReportService.getUserReports(user);
        model.addAttribute("scamReports", scamReports);
        return "scam-reports/manage";
    }

    @PostMapping
    public String submitScamReport(@ModelAttribute ScamReport scamReport, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        scamReportService.createScamReport(scamReport, userDetails);
        redirectAttributes.addFlashAttribute("message", "Scam report added successfully!");
        return "redirect:/scam-reports/feed";
    }

    @PostMapping("/delete/{postId}")
    public String deleteScamReport(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        try {
            scamReportService.deleteScamReport(postId, userDetails);
            redirectAttributes.addFlashAttribute("message", "Scam report deleted successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/scam-reports/manage";
    }
}
