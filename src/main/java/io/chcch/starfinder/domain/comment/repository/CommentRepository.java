package io.chcch.starfinder.domain.comment.repository;

import io.chcch.starfinder.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentRepository, Long> {

}
