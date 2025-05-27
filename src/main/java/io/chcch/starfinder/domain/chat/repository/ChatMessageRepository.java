package io.chcch.starfinder.domain.chat.repository;

import io.chcch.starfinder.domain.chat.entity.ChatMessage;
import io.chcch.starfinder.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
