package io.chcch.starfinder.domain.post.mapper;

import io.chcch.starfinder.domain.post.dto.PostCreateRequest;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.user.entity.User;


public class PostMapper {

    public static Post toEntity(PostCreateRequest request, User user) {

        return Post.builder()
            .content(request.getContent())
            .user(user)
            .build();
    }

}
