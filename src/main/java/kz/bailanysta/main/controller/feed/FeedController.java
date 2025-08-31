package kz.bailanysta.main.controller.feed;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kz.bailanysta.main.config.ApiConfig;
import kz.bailanysta.main.exception.InputException;
import kz.bailanysta.main.module.post.dto.PostDto;
import kz.bailanysta.main.service.PostService;
import kz.bailanysta.main.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = ApiConfig.PUBLIC_API_PREFIX + "feed")
@Api(tags = {"Лента"}, value = "Feed Controller")
@RequiredArgsConstructor
public class FeedController {

    private final UserService userService;
    private final PostService postService;

    @GetMapping("")
    @ApiOperation(tags = {"Кабинет"}, value = "Мои посты")
    public List<PostDto> getMyPosts(@RequestParam(defaultValue = "0") Integer page) throws InputException {
//        User user = userService.findCurrentUser();
//        Page<Post> posts = postService.findFeed(page, size);
//
//        List<PostDto> postDtos = posts.get()
//                .map(post -> new PostDto(post, user))
//                .toList();
//        return new PostsDto(postDtos, posts.getTotalPages());
        return new ArrayList<>();
    }
}
