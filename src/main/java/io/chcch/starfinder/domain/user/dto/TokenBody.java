package io.chcch.starfinder.domain.user.dto;

import io.chcch.starfinder.domain.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenBody {

    private Long userId;
    private Role role;

}
