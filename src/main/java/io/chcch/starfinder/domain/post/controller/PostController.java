package io.chcch.starfinder.domain.post.controller;

import io.chcch.starfinder.domain.post.dto.PostRequest;
import io.chcch.starfinder.domain.post.dto.PostListResponse;
import io.chcch.starfinder.domain.post.service.PostService;
import io.chcch.starfinder.global.dto.CursorSliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(
        @RequestBody PostRequest request
    ) {
        Long userId = 1L;
        postService.createPost(request, userId);

        return ResponseEntity.ok("게시글이 성공적으로 작성되었습니다.");
    }

    @GetMapping
    public ResponseEntity<CursorSliceResponse<PostListResponse>> getPosts(
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam Long userId
    ) {

        Slice<PostListResponse> slice = postService.getPosts(cursor, size, userId);
        return ResponseEntity.ok(CursorSliceResponse.of(slice));
    }

    @PatchMapping(value = "/{postId}")
    public ResponseEntity<?> updatePost(
        @RequestBody PostRequest request,
        @PathVariable Long postId
    ) {
        postService.updatePost(request, postId);

        return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
    }

    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<?> deletePost(
        @PathVariable Long postId
    ) {

        postService.deletePost(postId);

        return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
    }
}
