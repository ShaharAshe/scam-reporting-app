package hac.ex5.service;

import hac.ex5.model.ScamReport;
import hac.ex5.model.User;
import hac.ex5.repo.ScamReportRepository;
import hac.ex5.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ScamReportService {

    private final ScamReportRepository scamReportRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScamReportService(ScamReportRepository scamReportRepository, UserRepository userRepository) {
        this.scamReportRepository = scamReportRepository;
        this.userRepository = userRepository;
    }
    public List<ScamReport> getFeed(String sort)
    {
        List<ScamReport> scamReports;
        if ("oldest".equals(sort)) {
            scamReports = scamReportRepository.findAllByOrderByDateReportedAsc();
        } else {
            scamReports = scamReportRepository.findAllByOrderByDateReportedDesc();
        }

        return scamReports;
    }
    public List<ScamReport> getAllReportsOrdered() {
        return scamReportRepository.findAllByIdNotNullOrderByDateReported();
    }

    public List<ScamReport> getUserReports(User user) {
        return scamReportRepository.findAllByUserIdOrderByDateReportedDesc(user.getId());
    }

    public ScamReport createScamReport(ScamReport scamReport, UserDetails userDetails) {
        User user = userRepository.findByUsername(userDetails.getUsername());
        scamReport.setUser(user);
        scamReport.setDateReported(new Date());
        return scamReportRepository.save(scamReport);
    }

    public void deleteScamReport(Long postId, UserDetails userDetails) {
        ScamReport report = scamReportRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        User user = userRepository.findByUsername(userDetails.getUsername());

        if (report.getUser().getId().equals(user.getId())) {
            scamReportRepository.deleteById(postId);
        } else {
            throw new IllegalArgumentException("You can only delete your own posts!");
        }
    }
}
