package kz.bailanysta.main.module.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {


    @Query("SELECT p FROM Post p WHERE p.authorId = :userId " +
            "ORDER BY p.id DESC")
    Page<Post> findByUserId(Integer userId, Pageable pageable);

}
