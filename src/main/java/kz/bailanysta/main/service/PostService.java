package kz.bailanysta.main.service;

import kz.bailanysta.main.module.post.Post;
import kz.bailanysta.main.module.post.dto.PostDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    Post create(String text);
    PostDto edit(Integer postId, String text);

    Page<Post> findByUserId(Integer userId, int limit, int offset);
    List<PostDto> findFeed(Integer userId, int limit, int offset);
}
