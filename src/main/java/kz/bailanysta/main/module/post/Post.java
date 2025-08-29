package kz.bailanysta.main.module.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    private Integer id;

    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "description", nullable = false)
    private String description;

    // cached count of likes
    @Column(name = "likes_count")
    private Integer likesCount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
