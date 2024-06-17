package hac.ex5.model;


import jakarta.persistence.Id;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    // One user can post many scam reports
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ScamReport> scamReports;

    public void setFirstName(String firstName) {
        this.username = firstName;
    }
    public String getFirstName() {
        return this.username;
    }
    public void setLastName(String lastName) {
        this.password = lastName;
    }
    public String getLastName() {
        return this.password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }
    // Getters and Setters
}
