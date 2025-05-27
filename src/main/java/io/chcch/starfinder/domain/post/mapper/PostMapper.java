package io.chcch.starfinder.domain.post.mapper;

import io.chcch.starfinder.domain.post.dto.PostRequest;
import io.chcch.starfinder.domain.post.dto.PostResponse;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.user.entity.User;


public class PostMapper {

    public static Post toEntity(PostRequest request, User user) {

        return Post.builder()
            .content(request.getContent())
            .user(user)
            .build();
    }

    public static PostResponse from(Post post, User user) {
        return PostResponse.builder()
            .id(post.getId())
            .content(post.getContent())
            .createdAt(post.getCreatedAt())
            .nickname(user.getNickname())
            .profileUrl(user.getProfile_img())
            .build();
    }

}
