package io.chcch.starfinder.domain.user.service;

import io.chcch.starfinder.domain.user.dto.UserSignUpDto;
import io.chcch.starfinder.domain.user.entity.User;
import io.chcch.starfinder.domain.user.mapper.UserMapper;
import io.chcch.starfinder.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(UserSignUpDto userSignUpDto) {
        User user = UserMapper.toEntity(userSignUpDto);
        userRepository.save(user);

    }

    public boolean availableEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
