package io.chcch.starfinder.domain.post.repository;

import io.chcch.starfinder.domain.post.dto.PostListResponse;
import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
        SELECT new io.chcch.starfinder.domain.post.dto.PostListResponse(
            p.id,
            p.content,
            u.nickname,
            u.profileImg,
            p.createdAt,
            (SELECT COUNT(l) FROM Like l WHERE l.post = p),
            (SELECT COUNT(c) FROM Comment c WHERE c.post = p),
            u.id
        )
        FROM Post p
        JOIN p.user u
        WHERE (:cursor IS NULL OR p.id < :cursor)
        ORDER BY p.id DESC
    """)
    List<PostListResponse> findNextPage(@Param("cursor") Long cursor, Pageable pageable);

}
