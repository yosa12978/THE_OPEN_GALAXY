package com.yosa.theopengalaxy.domain;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NonNull
    private RateType rateType;
    private String text;
    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date pubDate;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Account author;
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    public Review() {
    }

    public Review(@NonNull RateType rateType, String text, @NonNull Account author, @NonNull Project project) {
        this.rateType = rateType;
        this.text = text;
        this.author = author;
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(@NonNull RateType rateType) {
        this.rateType = rateType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NonNull
    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(@NonNull Date pubDate) {
        this.pubDate = pubDate;
    }

    @NonNull
    public Account getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull Account author) {
        this.author = author;
    }

    @NonNull
    public Project getProject() {
        return project;
    }

    public void setProject(@NonNull Project project) {
        this.project = project;
    }
}
