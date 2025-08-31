package kz.bailanysta.main.module.post.dto;


import java.util.List;

public record PostsDto(List<PostDto> posts, int total) {
}
