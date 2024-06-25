package hac.ex5.repo;

import hac.ex5.model.ScamReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for handling the database operations for ScamReport entities.
 */
public interface ScamReportRepository extends JpaRepository<ScamReport, Long> {

    /**
     * Find all scam reports, ensuring they have an ID and ordered by the date reported.
     * @return List of all scam reports.
     */
    List<ScamReport> findAllByIdNotNullOrderByDateReported();

    /**
     * Count all scam reports where the date reported is true.
     * @return count of scam reports.
     */
    long countAllByDateReportedIsTrue();

    /**
     * Delete a scam report by its ID.
     * @param id The ID of the scam report to delete.
     */
    void deleteById(Long id);

    /**
     * Find all scam reports by a user's ID.
     * @param id User ID.
     * @return List of scam reports.
     */
    List<ScamReport> findAllByUserId(Long id);

    /**
     * Find all scam reports by a user's ID and order them by date reported in descending order.
     * @param id User ID.
     * @return List of ordered scam reports.
     */
    List<ScamReport> findAllByUserIdOrderByDateReportedDesc(Long id);

    /**
     * Find all scam reports and order them by date reported in descending order.
     * @return List of ordered scam reports.
     */
    List<ScamReport> findAllByOrderByDateReportedDesc();

    /**
     * Find all scam reports and order them by date reported in ascending order.
     * @return List of ordered scam reports.
     */
    List<ScamReport> findAllByOrderByDateReportedAsc();
}
