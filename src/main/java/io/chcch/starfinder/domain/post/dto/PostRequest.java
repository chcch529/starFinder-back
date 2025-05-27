package io.chcch.starfinder.domain.post.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRequest {

    private String content;

}
