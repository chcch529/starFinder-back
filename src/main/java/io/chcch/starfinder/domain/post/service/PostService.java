package io.chcch.starfinder.domain.post.service;

import io.chcch.starfinder.domain.post.dto.PostCreateRequest;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.post.mapper.PostMapper;
import io.chcch.starfinder.domain.post.repository.PostRepository;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Long createPost(PostCreateRequest request, Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(RuntimeException::new);

        Post post = PostMapper.toEntity(request, user);

        return postRepository.save(post).getId();
    }



}
