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
    private String description;
    private Date dateReported;

    // Many scam reports can be posted by one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // Getters and Setters

    // One scam report can have many comments
    @OneToMany(mappedBy = "scamReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;



}