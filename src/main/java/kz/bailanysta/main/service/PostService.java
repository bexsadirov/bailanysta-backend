package kz.bailanysta.main.service;

import kz.bailanysta.main.module.post.dto.PostDto;

import java.util.List;

public interface PostService {

    PostDto create(Integer userId, String text);
    PostDto edit(Integer postId, String text);

    List<PostDto> findFeed(Integer userId, int limit, int offset);
}
