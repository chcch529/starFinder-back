package io.chcch.starfinder.domain.post.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String content;
    private String nickname;
    private String profileUrl;
    private LocalDateTime createdAt;


}
