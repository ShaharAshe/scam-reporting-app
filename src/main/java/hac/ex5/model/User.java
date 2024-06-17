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


    private String firstName;
    private String lastName;
    // One user can post many scam reports
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ScamReport> scamReports;


    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }



    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return this.firstName;
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

    public Long getId() {
        return this.id;
    }
    // Getters and Setters
}
