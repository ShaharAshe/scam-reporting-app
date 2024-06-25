package hac.ex5.service;

import hac.ex5.model.ScamReport;
import hac.ex5.model.User;
import hac.ex5.repo.ScamReportRepository;
import hac.ex5.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Service for managing scam reports.
 */
@Service
public class ScamReportService {

    private final ScamReportRepository scamReportRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScamReportService(ScamReportRepository scamReportRepository, UserRepository userRepository) {
        this.scamReportRepository = scamReportRepository;
        this.userRepository = userRepository;
    }

    /**
     * Fetch scam reports sorted by date in ascending or descending order.
     * @param sort The sort order ("newest" or "oldest").
     * @return List of sorted scam reports.
     */
    public List<ScamReport> getFeed(String sort) {
        return sort.equals("oldest") ? scamReportRepository.findAllByOrderByDateReportedAsc() : scamReportRepository.findAllByOrderByDateReportedDesc();
    }
    public List<ScamReport> getAllReportsOrdered() {
        return scamReportRepository.findAllByIdNotNullOrderByDateReported();
    }

    public List<ScamReport> getUserReports(User user) {
        return scamReportRepository.findAllByUserIdOrderByDateReportedDesc(user.getId());
    }
    /**
     * Create a new scam report and associate it with the user.
     * @param scamReport The scam report to create.
     * @param userDetails The current authenticated user.
     * @return The saved scam report.
     */
    public ScamReport createScamReport(ScamReport scamReport, UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        scamReport.setUser(user);
        scamReport.setDateReported(new Date());
        return scamReportRepository.save(scamReport);
    }
    public List<ScamReport> getScamReportsByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        return scamReportRepository.findAllById(ids);
    }
    /**
     * Delete a scam report if the user has appropriate permissions.
     * @param postId The ID of the scam report to delete.
     * @param userDetails The current authenticated user.
     */
    public void deleteScamReport(Long postId, UserDetails userDetails) {
        ScamReport report = scamReportRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        User user = userRepository.findByUsername(userDetails.getUsername());

        if (report.getUser().equals(user) || "ADMIN".equals(user.getRole())) {
            scamReportRepository.deleteById(postId);
        } else {
            throw new IllegalArgumentException("You can only delete your own posts or as an admin!");
        }
    }
}
