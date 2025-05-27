package io.chcch.starfinder.domain.post.repository;

import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.user.entity.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p " +
    "WHERE (:cursor IS NULL OR p.id < :cursor) " +
    "ORDER BY p.id DESC ")
    List<Post> findNextPage(@Param("cursor") Long cursor, Pageable pageable);

}
