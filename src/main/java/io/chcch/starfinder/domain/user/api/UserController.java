package io.chcch.starfinder.domain.user.api;

import io.chcch.starfinder.domain.user.dto.LoginRequest;
import io.chcch.starfinder.domain.user.dto.LoginResponse;
import io.chcch.starfinder.domain.user.dto.UserSignUpDto;
import io.chcch.starfinder.domain.user.service.UserService;
import io.chcch.starfinder.global.dto.ApiResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> saveUser(@RequestBody UserSignUpDto dto) {
        userService.save(dto);

        return ApiResponse.success(
            "성공적으로 가입되었습니다.",
            null
        );
    }

    @PostMapping("/email/exist")
    public ResponseEntity<ApiResponse<Boolean>> availableEmail(@RequestBody String email) {

        if (!userService.availableEmail(email)) {
            return ResponseEntity.ok()
                .body(ApiResponse.success(
                    "가입 가능한 이메일입니다.",
                    true
                ));

        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ApiResponse.success(
                "이미 가입되어 있는 이메일입니다.",
                false
            ));

    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = userService.authenticateAndGenerateToken(request);

        return ApiResponse.success(
            "로그인에 성공하였습니다.",
            loginResponse
        );
    }
}
