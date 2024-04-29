package org.admin.controller;

import lombok.RequiredArgsConstructor;
import org.admin.domain.User;
import org.admin.security.dto.UserLoginDto;
import org.admin.security.dto.UserLoginResponseDto;
import org.admin.security.dto.UserSignupDto;
import org.admin.security.dto.UserSignupResponseDto;
import org.admin.security.jwt.util.JwtTokenizer;
import org.admin.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private static final Log log = LogFactory.getLog(AuthController.class);
    private final UserService userService;
    private final JwtTokenizer jwtTokenizer;
//    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String login() {
        return "auth/form";
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid UserSignupDto userSignupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        }
        User newUser = User.builder()
                .name(userSignupDto.getName())
                .email(userSignupDto.getEmail())
                .password(passwordEncoder.encode(userSignupDto.getPassword()))
                .build();

        userService.add(newUser);

        UserSignupResponseDto userSignupResponseDto =
                UserSignupResponseDto.builder()
                .userNo(newUser.getUserNo())
                .name(newUser.getName())
                .email(newUser.getEmail())
                .regdate(newUser.getRegDate())
                .build();

        return new ResponseEntity(userSignupResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserLoginDto loginDto) {

        // TODO email에 해당하는 사용자 정보를 읽어와서 암호가 맞는지 검사하는 코드가 있어야 한다.
        Long memberId = 1L;
        String email = loginDto.getEmail();
        List<String> roles = List.of("ROLE_USER");

        // JWT토큰을 생성하였다. jwt라이브러리를 이용하여 생성.
        String accessToken = jwtTokenizer.createAccessToken(memberId, email, roles);
        String refreshToken = jwtTokenizer.createRefreshToken(memberId, email, roles);

        UserLoginResponseDto loginResponse = UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(memberId)
                .nickname("nickname")
                .build();
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token) {
        // token repository에서 refresh Token에 해당하는 값을 삭제한다.
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
