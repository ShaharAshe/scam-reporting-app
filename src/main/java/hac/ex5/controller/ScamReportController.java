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

import java.util.Date;

@Controller
public class ScamReportController {

    @Autowired
    private ScamReportRepository scamReportRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/posts")
    public String showScamReportForm(Model model) {
        model.addAttribute("scamReport", new ScamReport());
        return "admin/posts";
    }

    @PostMapping("/posts")
    public String submitScamReport(@ModelAttribute ScamReport scamReport, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        scamReport.setUser(user);
        scamReport.setDateReported(new Date());
        scamReportRepository.save(scamReport);
        return "redirect:/";
    }
}
