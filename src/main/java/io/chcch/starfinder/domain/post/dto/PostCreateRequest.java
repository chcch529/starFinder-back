package io.chcch.starfinder.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostCreateRequest {

    private String content;

}
