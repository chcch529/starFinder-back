package io.chcch.starfinder.domain.post.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.post.repository.PostRepository;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.repository.UserRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        String uniqueEmail = "test" + System.nanoTime() + "@sample.com";
        user = userRepository.save(
            User.builder()
                .name("테스트")
                .nickname("테스트유저")
                .provider("test")
                .email(uniqueEmail)
                .password("1234")
                .birthDay(LocalDate.now())
                .build()
        );

        for (long i = 1; i <= 15; i++) {
            postRepository.save(
                Post.builder()
                    .user(user)
                    .content(i + "번 게시글")
                    .build()
            );
        }
    }

    @Test
    @DisplayName("커서 없이 요청 보냈을 때 정상 응답 확인")
    void getPosts_shouldReturnFirstSlice() throws Exception {
        mockMvc.perform(get("/api/posts")
                .param("size", "10")
                .param("userId", user.getId().toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.content.length()").value(10))
            .andExpect(jsonPath("$.data.hasNext").value(true));
    }

    @Test
    @DisplayName("두번째 페이지가 잘 불러와지는지 확인")
    void getPosts_withCursor_shouldReturnNextSlice() throws Exception {
        // 첫 페이지를 요청해 커서 추출
        MvcResult result = mockMvc.perform(get("/api/posts")
                .param("size", "10")
                .param("userId", user.getId().toString()))
            .andExpect(status().isOk())
            .andReturn();

        String json = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        Long lastId = root.get("data").get("content").get(9).get("id").asLong();

        // 다음 페이지 요청
        mockMvc.perform(get("/api/posts")
                .param("size", "10")
                .param("userId", user.getId().toString())
                .param("cursor", lastId.toString()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.content.length()").value(5))
            .andExpect(jsonPath("$.data.hasNext").value(false));
    }

}