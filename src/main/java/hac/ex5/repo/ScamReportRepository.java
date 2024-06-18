package hac.ex5.repo;

import hac.ex5.model.ScamReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScamReportRepository extends JpaRepository<ScamReport, Long> {
}