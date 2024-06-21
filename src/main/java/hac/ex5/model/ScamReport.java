package hac.ex5.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class ScamReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String url;

    private String description;
    private Date dateReported;

    // Many scam reports can be posted by one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters and Setters
    public String getUrl()
    { return url; }
    public void setUrl(String url) { this.url = url; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
