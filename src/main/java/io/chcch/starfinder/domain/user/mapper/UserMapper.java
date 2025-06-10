package io.chcch.starfinder.domain.user.mapper;

import io.chcch.starfinder.domain.user.dto.UserSignUpDto;
import io.chcch.starfinder.domain.user.entity.User;

public class UserMapper {

    public static User toEntity(UserSignUpDto dto) {
        return User.builder()
            .email(dto.getEmail())
            .name(dto.getName())
            .nickname(dto.getNickname())
            .password(dto.getPassword())
            .provider("LOCAL")
            .build();
    }

}
