package io.chcch.starfinder.domain.like.repository;

import io.chcch.starfinder.domain.like.entity.Like;
import io.chcch.starfinder.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    int countByPost(Post post);

}
