package kz.bailanysta.main.controller.cabinet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.bailanysta.main.config.ApiConfig;
import kz.bailanysta.main.exception.InputException;
import kz.bailanysta.main.module.post.Post;
import kz.bailanysta.main.module.post.dto.PostCreateBody;
import kz.bailanysta.main.module.post.dto.PostDto;
import kz.bailanysta.main.module.post.dto.PostsDto;
import kz.bailanysta.main.module.user.CurrUser;
import kz.bailanysta.main.module.user.User;
import kz.bailanysta.main.service.PostService;
import kz.bailanysta.main.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = ApiConfig.PRIVATE_API_PREFIX + "cabinet")
@Api(tags = {"Кабинет"}, value = "PostController")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserService userService;

    @GetMapping("posts")
    @ApiOperation(tags = {"Кабинет"}, value = "Мои посты")
    public PostsDto getMyPosts(@RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "10") Integer size) throws InputException {
        User user = userService.findCurrentUser();
        Page<Post> posts = postService.findByUserId(CurrUser.getId(), page, size);

        List<PostDto> postDtos = posts.get()
                .map(post -> new PostDto(post, user))
                .toList();
        return new PostsDto(postDtos, posts.getTotalPages());
    }

    @PostMapping("posts")
    @ApiOperation(tags = {"Кабинет"}, value = "Создать новый пост")
    public PostDto createNewPost(@RequestBody PostCreateBody body) {
        Post post = postService.create(body.text());
        return new PostDto(post, userService.findCurrentUser());
    }
}
