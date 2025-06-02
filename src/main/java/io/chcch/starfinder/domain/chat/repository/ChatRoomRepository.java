package io.chcch.starfinder.domain.chat.repository;

import io.chcch.starfinder.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
