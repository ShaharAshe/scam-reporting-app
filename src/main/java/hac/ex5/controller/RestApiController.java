package hac.ex5.controller;

import hac.ex5.model.ScamReport;
import hac.ex5.repo.ScamReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private final ScamReportRepository scamReportRepository;

    @Autowired
    public RestApiController(ScamReportRepository scamReportRepository) {
        this.scamReportRepository = scamReportRepository;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<ScamReport>> getAllPosts() {
        List<ScamReport> scamReports = scamReportRepository.findAll();
        return ResponseEntity.ok(scamReports);
    }
}
