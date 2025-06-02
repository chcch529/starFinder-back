package io.chcch.starfinder.domain.comment.repository;

import io.chcch.starfinder.domain.comment.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<PostComment, Long> {

}
