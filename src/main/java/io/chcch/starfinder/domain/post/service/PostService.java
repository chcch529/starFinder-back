package io.chcch.starfinder.domain.post.service;

import io.chcch.starfinder.domain.post.dto.PostRequest;
import io.chcch.starfinder.domain.post.dto.PostListResponse;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.post.mapper.PostMapper;
import io.chcch.starfinder.domain.post.repository.PostRepository;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.repository.UserRepository;
import io.chcch.starfinder.global.util.SliceUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long createPost(PostRequest request, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(RuntimeException::new);

        Post post = PostMapper.toEntity(request, user);

        return postRepository.save(post).getId();
    }

    public Slice<PostListResponse> getPosts(Long cursor, int size, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(RuntimeException::new);

        Sort sort = Sort.by(Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, size + 1, sort);
        List<PostListResponse> posts = postRepository.findNextPage(cursor, pageable);

        return SliceUtil.toSlice(posts, PageRequest.of(0, size, sort));
    }

    public void updatePost(PostRequest request, Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(RuntimeException::new);

        post.update(request);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(RuntimeException::new);

        postRepository.delete(post);
    }



}
