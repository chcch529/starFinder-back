package io.chcch.starfinder.domain.post.mapper;

import io.chcch.starfinder.domain.post.dto.PostRequest;
import io.chcch.starfinder.domain.post.dto.PostListResponse;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.user.entity.User;


public class PostMapper {

    public static Post toEntity(PostRequest request, User user) {

        return Post.builder()
            .content(request.getContent())
            .user(user)
            .build();
    }
//
//    public static PostResponse from(PostListResponse dto) {
//
//        return PostResponse.builder()
//            .id(dto.id())
//            .content(dto.content())
//            .nickname(dto.nickname())
//            .profileUrl(dto.profileUrl())
//            .createdAt(dto.createdAt())
//            .likeCnt(dto.likeCnt())
//            .commentCnt(dto.commentCnt())
//            .userId(dto.userId())
//            .build();
//    }

}
