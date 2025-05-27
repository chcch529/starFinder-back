package io.chcch.starfinder.domain.post.controller;

import io.chcch.starfinder.domain.post.dto.PostCreateRequest;
import io.chcch.starfinder.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(
        @RequestBody PostCreateRequest request
    ) {
        Long userId = 1L;
        postService.createPost(request, userId);

        return ResponseEntity.ok("게시글이 성공적으로 작성되었습니다.");
    }

    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<?> deletePost(
        @PathVariable Long postId
    ) {
        postService.deletePost(postId);

        return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
    }
}
