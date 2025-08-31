package kz.bailanysta.main.module.post;

import kz.bailanysta.main.module.post.dto.PostDto;
import kz.bailanysta.main.module.user.CurrUser;
import kz.bailanysta.main.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(rollbackOn = Throwable.class)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Post create(String text) {
        Post post = new Post();
        post.setAuthorId(CurrUser.getId());
        post.setTextContent(text);
        post.setLikesCount(0);
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
        return post;
    }

    @Override
    public PostDto edit(Integer postId, String text) {
        return null;
    }

    @Override
    public Page<Post> findByUserId(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findByUserId(userId, pageable);
    }

    @Override
    public List<PostDto> findFeed(Integer userId, int limit, int offset) {
        return List.of();
    }
}
