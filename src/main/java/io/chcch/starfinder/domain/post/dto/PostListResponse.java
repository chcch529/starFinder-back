package io.chcch.starfinder.domain.post.dto;

import java.time.LocalDateTime;

public record PostListResponse(
    Long id,
    String content,
    String nickname,
    String profileUrl,
    LocalDateTime createdAt,
    int likeCnt,
    int commentCnt,
    Long userId

) {

}
