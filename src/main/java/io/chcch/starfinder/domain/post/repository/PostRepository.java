package io.chcch.starfinder.domain.post.repository;

import io.chcch.starfinder.domain.post.entity.Post;
import io.chcch.starfinder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
