package kz.bailanysta.main.service;

import kz.bailanysta.main.module.post.Post;
import kz.bailanysta.main.module.post.dto.PostDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    Post create(String text);
    Post edit(Integer postId, String text);
    void delete(Integer postId);

    /*
     * Post
     */
    Post findById(Integer id);
    Page<Post> findByUserId(Integer userId, int limit, int offset);
    List<PostDto> findFeed(Integer userId, int limit, int offset);
}
