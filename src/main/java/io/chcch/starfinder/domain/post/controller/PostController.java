package io.chcch.starfinder.domain.post.controller;

import io.chcch.starfinder.domain.post.dto.PostRequest;
import io.chcch.starfinder.domain.post.dto.PostListResponse;
import io.chcch.starfinder.domain.post.service.PostService;
import io.chcch.starfinder.global.dto.ApiResponse;
import io.chcch.starfinder.global.dto.CursorSliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createPost(
        @RequestBody PostRequest request
    ) {
        Long userId = 1L;
        postService.createPost(request, userId);

        return ApiResponse.success("게시글이 성공적으로 생성되었습니다.", null);
    }

    @GetMapping
    public ApiResponse<CursorSliceResponse<PostListResponse>> getPosts(
        @RequestParam(required = false) Long cursor,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam Long userId
    ) {

        Slice<PostListResponse> slice = postService.getPosts(cursor, size, userId);
        return ApiResponse.success("게시글 목록이 성공적으로 조회되었습니다.", CursorSliceResponse.of(slice));
    }

    @PatchMapping(value = "/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> updatePost(
        @RequestBody PostRequest request,
        @PathVariable Long postId
    ) {
        postService.updatePost(request, postId);

        return ApiResponse.success("게시글이 성공적으로 수정되었습니다.", null);
    }

    @DeleteMapping(value = "/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deletePost(
        @PathVariable Long postId
    ) {

        postService.deletePost(postId);

        return ApiResponse.success("게시글이 성공적으로 삭제되었습니다.", null);
    }

}
