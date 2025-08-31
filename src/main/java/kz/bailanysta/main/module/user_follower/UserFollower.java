package kz.bailanysta.main.module.user_follower;

import javax.persistence.*;

@Entity
@Table(name = "user_follower")
public class UserFollower {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer userId;

    @Column(name = "follower_id", nullable = false, updatable = false)
    private Integer followerId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;
}
