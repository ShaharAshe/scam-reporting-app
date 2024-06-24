package hac.ex5.controller;

import hac.ex5.model.ScamReport;
import hac.ex5.model.User;
import hac.ex5.repo.UserRepository;
import hac.ex5.service.ScamReportService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/scam-reports")
public class ScamReportController {

    private final ScamReportService scamReportService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ScamReportController(ScamReportService scamReportService, UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.scamReportService = scamReportService;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/new")
    public String showScamReportForm(Model model) {
        model.addAttribute("scamReport", new ScamReport());
        return "scam-reports/create";
    }

    @GetMapping("/feed")
    public String showFeed(@RequestParam(defaultValue = "newest") String sort,Model model) {
        List<ScamReport> scamReports = scamReportService.getFeed(sort);
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

    @GetMapping("/admin")
    public String showAdmin(@RequestParam(defaultValue = "newest") String sort,Model model) {
        List<ScamReport> scamReports = scamReportService.getFeed(sort);
        model.addAttribute("scamReports", scamReports);
        return "admin/admin";
    }

    @PostMapping
    public String submitScamReport(@ModelAttribute ScamReport scamReport, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        scamReportService.createScamReport(scamReport, userDetails);
        redirectAttributes.addFlashAttribute("message", "Scam report added successfully!");
        messagingTemplate.convertAndSend("/topic/scamReports", "Post is added");
        return "redirect:/scam-reports/feed";
    }

    @PostMapping("/delete/{postId}")
    public String deleteScamReport(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        try {
            scamReportService.deleteScamReport(postId, userDetails);
            messagingTemplate.convertAndSend("/topic/scamReports", "Report with ID " + postId + " deleted");
            redirectAttributes.addFlashAttribute("message", "Scam report deleted successfully!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }

        User user = userRepository.findByUsername(userDetails.getUsername());

        if (user.getRole().equals("ADMIN"))
            return "redirect:/admin";
        return "redirect:/scam-reports/manage";
    }

    @PostMapping("/likePost")
    public String likePost(@RequestParam("postId") Long postId, HttpSession session, RedirectAttributes redirectAttributes) {
        List<Long> likedPosts = (List<Long>) session.getAttribute("likedPosts");
        if (likedPosts == null) {
            likedPosts = new ArrayList<>();
        }
        if (!likedPosts.contains(postId)) {
            likedPosts.add(postId);
            redirectAttributes.addFlashAttribute("message", "Post liked successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Post is already liked.");
        }
        session.setAttribute("likedPosts", likedPosts);
        return "redirect:/scam-reports/feed";
    }

    @PostMapping("/unlikePost")
    public String unlikePost(@RequestParam("postId") Long postId, HttpSession session, RedirectAttributes redirectAttributes) {
        List<Long> likedPosts = (List<Long>) session.getAttribute("likedPosts");
        if (likedPosts == null) {
            likedPosts = new ArrayList<>();
        }
        if (likedPosts.contains(postId)) {
            likedPosts.remove(postId);
            redirectAttributes.addFlashAttribute("message", "Post Unliked successfully!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Post is already Unliked.");
        }
        session.setAttribute("likedPosts", likedPosts);
        return "redirect:/scam-reports/likedPosts";
    }

    @GetMapping("/likedPosts")
    public String showLikedPosts(Model model, HttpSession session) {
        List<Long> likedPostIds = (List<Long>) session.getAttribute("likedPosts");
        List<ScamReport> likedPosts = scamReportService.getScamReportsByIds(likedPostIds);
        model.addAttribute("likedPosts", likedPosts);
        return "scam-reports/likedPosts";
    }
}
