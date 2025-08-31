package kz.bailanysta.main.module.post.like;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "post__like")
public class PostLike {

    @Id
    private Integer id;

    @Column(name = "post_id", nullable = false, updatable = false)
    private Integer postId;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Integer userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
