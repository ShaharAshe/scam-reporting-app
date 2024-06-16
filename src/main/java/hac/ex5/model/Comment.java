package hac.ex5.model;


import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    // Many comments can be on one scam report
    @ManyToOne
    @JoinColumn(name = "scam_report_id")
    private ScamReport scamReport;

    // Getters and Setters
}