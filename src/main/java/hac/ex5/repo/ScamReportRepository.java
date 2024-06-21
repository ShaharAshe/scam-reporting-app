package hac.ex5.repo;

import hac.ex5.model.ScamReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScamReportRepository extends JpaRepository<ScamReport, Long> {

    List<ScamReport> findAllByIdNotNullOrderByDateReported();

}