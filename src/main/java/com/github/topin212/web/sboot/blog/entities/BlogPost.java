package com.github.topin212.web.sboot.blog.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Entity(name = "post")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column(name = "post_title")
    private String postTitle;
    @Column(name = "post_text")
    private String postText;

    @Column(name = "post_date")
    private OffsetDateTime postDate;
    @Column(name = "like_count")
    private long thumbsUpCount;

    @Column(name = "is_Deleted")
    private boolean isActive;

    public BlogPost() {
    }

    public BlogPost(Publisher publisher, String postTitle, String postText) {
        this.publisher = publisher;
        this.postTitle = postTitle;
        this.postText = postText;
        this.postDate = LocalDateTime.now(ZoneId.of("UTC")).atOffset(ZoneOffset.UTC);
        this.isActive = true;
        this.thumbsUpCount = 0;
    }

    public boolean isValid(){
        return publisher != null && postTitle != null && postText != null;
    }

    public void giveALike() {
        this.thumbsUpCount++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public OffsetDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(OffsetDateTime postDate) {
        this.postDate = postDate;
    }

    public long getThumbsUpCount() {
        return thumbsUpCount;
    }

    public void setThumbsUpCount(long thumbsUpCount) {
        this.thumbsUpCount = thumbsUpCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
