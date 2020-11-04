package com.yosa.theopengalaxy.domain;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String url;
    @NonNull
    private Long rate = 0L;
    private String description;
    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date pubDate;
    @NonNull
    private Long views = 0L;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Account author;
    @ElementCollection
    private List<String> tags;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Review> reviews;
    private String image;

    public Project() {
    }

    public Project(@NonNull String title, @NonNull String url, @NonNull Account author) {
        this.title = title;
        this.url = url;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public Long getRate() {
        return rate;
    }

    public void setRate(@NonNull Long rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(@NonNull Date pubDate) {
        this.pubDate = pubDate;
    }

    @NonNull
    public Long getViews() {
        return views;
    }

    public void setViews(@NonNull Long views) {
        this.views = views;
    }

    @NonNull
    public Account getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull Account author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
