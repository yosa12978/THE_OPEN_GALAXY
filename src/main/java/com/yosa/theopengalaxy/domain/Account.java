package com.yosa.theopengalaxy.domain;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles;
    private String email;
    @NonNull
    private String token;
    @NonNull
    private String emailToken;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Project> projects;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private List<Review> reviews;
    private String userPic = "/img/DefaultPic.png";
    private Boolean enabled = false;

    public Account() {
    }

    public Account(@NonNull String username, @NonNull String password, @NonNull Set<Role> roles, String email, @NonNull String token, @NonNull String emailToken) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
        this.token = token;
        this.emailToken = emailToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(@NonNull Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    public String getToken() {
        return token;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }

    @NonNull
    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(@NonNull String emailToken) {
        this.emailToken = emailToken;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getUserPic() {
        return userPic;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
