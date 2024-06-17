package hac.ex5.model;

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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ScamReport getScamReport() {
        return scamReport;
    }

    public void setScamReport(ScamReport scamReport) {
        this.scamReport = scamReport;
    }
}
