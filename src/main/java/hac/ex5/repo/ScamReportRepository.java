package hac.ex5.repo;

import hac.ex5.model.ScamReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ScamReportRepository extends JpaRepository<ScamReport, Long> {

    List<ScamReport> findAllByIdNotNullOrderByDateReported();

    long countAllByDateReportedIsTrue();




    void deleteById(Long id);
    List<ScamReport> findAllByUserId(Long id);

    List<ScamReport> findAllByUserIdOrderByDateReportedDesc(Long id);

    List<ScamReport> findAllByOrderByDateReportedDesc();

    List<ScamReport> findAllByOrderByDateReportedAsc();
}