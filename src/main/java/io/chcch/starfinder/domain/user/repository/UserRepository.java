package io.chcch.starfinder.domain.user.repository;

import io.chcch.starfinder.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
