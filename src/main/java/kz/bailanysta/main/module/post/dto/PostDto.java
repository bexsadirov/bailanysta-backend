package kz.bailanysta.main.module.post.dto;

import kz.bailanysta.main.module.post.Post;
import kz.bailanysta.main.module.user.User;

public record PostDto(Integer id, String textContent, String authorUsername) {

    public PostDto(Post post, User author) {
        this(post.getId(), post.getTextContent(), author.getUsername());
    }

}
