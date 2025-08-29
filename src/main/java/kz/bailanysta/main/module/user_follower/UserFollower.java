package kz.bailanysta.main.module.user_follower;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserFollower {

    @Id
    private Integer id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer userId;

    @Column(name = "follower_id", nullable = false, updatable = false)
    private Integer followerId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;
}
