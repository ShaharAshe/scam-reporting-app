package hac.ex5.service;

import hac.ex5.repo.ScamReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScamReportService {

    private final ScamReportRepository scamReportRepository;

    @Autowired
    public ScamReportService(ScamReportRepository scamReportRepository) {
        this.scamReportRepository = scamReportRepository;
    }

    @Transactional
    public void deleteScamReport(Long id) {
        scamReportRepository.deleteById(id);
    }
}
