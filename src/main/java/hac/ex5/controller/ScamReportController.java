package hac.ex5.controller;

import hac.ex5.model.ScamReport;
import hac.ex5.model.User;
import hac.ex5.repo.ScamReportRepository;
import hac.ex5.repo.UserRepository;
import hac.ex5.service.ScamReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/scam-reports")
public class ScamReportController {

    private final ScamReportRepository scamReportRepository;
    private final UserRepository userRepository;
    private final ScamReportService scamReportService;
    @Autowired
    public ScamReportController(ScamReportRepository scamReportRepository, UserRepository userRepository, ScamReportService scamReportService) {
        this.scamReportRepository = scamReportRepository;
        this.userRepository = userRepository;
        this.scamReportService = scamReportService;
    }

    @GetMapping("/new")
    public String showScamReportForm(Model model) {
        model.addAttribute("scamReport", new ScamReport());
        return "scam-reports/create";
    }

    @GetMapping("/feed")
    public String showFeed(Model model) {
        List<ScamReport> scamReports = scamReportRepository.findAllByOrderByDateReportedDesc();
        model.addAttribute("scamReports", scamReports);
        return "scam-reports/feed";
    }

    @GetMapping("/manage")
    public String showUserPosts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<ScamReport> scamReports = scamReportRepository.findAllByUserIdOrderByDateReportedDesc(user.getId());
        model.addAttribute("scamReports", scamReports);
        return "scam-reports/manage";
    }

    @PostMapping
    public String submitScamReport(@ModelAttribute ScamReport scamReport, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        scamReport.setUser(user);
        scamReport.setDateReported(new Date());
        scamReportRepository.save(scamReport);
        redirectAttributes.addFlashAttribute("message", "Scam report added successfully!");
        return "redirect:/scam-reports/feed";
    }

    @DeleteMapping("delete/{postId}")
    public String deleteScamReport(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        ScamReport report = scamReportRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        User user = userRepository.findByUsername(userDetails.getUsername());

        if (report.getUser().getId().equals(user.getId())) {
            scamReportRepository.deleteById(postId);
            redirectAttributes.addFlashAttribute("message", "Scam report deleted successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "You can only delete your own posts!");
        }

        return "redirect:/scam-reports/manage";
    }


}
