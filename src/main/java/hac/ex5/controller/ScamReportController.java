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

/**
 * Controller for handling requests related to scam reports. 
 * Provides endpoints for creating, viewing, managing, and deleting scam reports.
 */
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

    // Displays the form to create a new scam report
    @GetMapping("/new")
    public String showScamReportForm(Model model) {
        model.addAttribute("scamReport", new ScamReport());
        return "scam-reports/create";
    }

    // Displays all scam reports with sorting options
    @GetMapping("/feed")
    public String showFeed(@RequestParam(defaultValue = "newest") String sort, Model model) {
        List<ScamReport> scamReports = scamReportService.getFeed(sort);
        model.addAttribute("scamReports", scamReports);
        return "scam-reports/feed";
    }

    // Displays scam reports managed by the currently authenticated user
    @GetMapping("/manage")
    public String showUserPosts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        List<ScamReport> scamReports = scamReportService.getUserReports(user);
        model.addAttribute("scamReports", scamReports);
        return "scam-reports/manage";
    }

    // Displays admin-specific view of scam reports with sorting
    @GetMapping("/admin")
    public String showAdmin(@RequestParam(defaultValue = "newest") String sort, Model model) {
        List<ScamReport> scamReports = scamReportService.getFeed(sort);
        model.addAttribute("scamReports", scamReports);
        return "admin/admin";
    }

    // Processes the submission of a new scam report and broadcasts an event
    @PostMapping
    public String submitScamReport(@ModelAttribute ScamReport scamReport, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        scamReportService.createScamReport(scamReport, userDetails);
        redirectAttributes.addFlashAttribute("message", "Scam report added successfully!");
        messagingTemplate.convertAndSend("/topic/scamReports", "Post is added");
        return "redirect:/scam-reports/feed";
    }

    // Handles the deletion of a scam report and broadcasts an event
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

    // Likes a post and updates the session attributes
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

    // Unlikes a post and updates the session attributes
    @PostMapping("/unlikePost")
    public String unlikePost(@RequestParam("postId") Long postId, HttpSession session, RedirectAttributes redirectAttributes) {
        List<Long> likedPosts = (List<Long>) session.getAttribute("likedPosts");
        if (likedPosts == null ) {
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

    // Displays posts liked by the user
    @GetMapping("/likedPosts")
    public String showLikedPosts(Model model, HttpSession session) {
        List<Long> likedPostIds = (List<Long>) session.getAttribute("likedPosts");
        List<ScamReport> likedPosts = scamReportService.getScamReportsByIds(likedPostIds);
        model.addAttribute("likedPosts", likedPosts);
        return "scam-reports/likedPosts";
    }
}
