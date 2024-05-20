package org.admin.controller;

import io.jsonwebtoken.Claims;
import org.admin.domain.Member;
import lombok.RequiredArgsConstructor;
import org.admin.domain.RefreshToken;
import org.admin.domain.Role;
import org.admin.security.dto.*;
import org.admin.security.jwt.util.JwtTokenizer;
import org.admin.service.MemberService;
import org.admin.service.RefreshTokenService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private static final Log log = LogFactory.getLog(AuthController.class);
    private final MemberService memberService;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid MemberSignupDto memberSignupDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Member newMember = Member.builder()
                .name(memberSignupDto.getName())
                .email(memberSignupDto.getEmail())
                .password(passwordEncoder.encode(memberSignupDto.getPassword()))
                .build();

        memberService.add(newMember);

        MemberSignupResponseDto memberSignupResponseDto =
                MemberSignupResponseDto.builder()
                        .memberNo(newMember.getMemberNo())
                        .name(newMember.getName())
                        .email(newMember.getEmail())
                        .regdate(newMember.getJoinDate())
                        .build();

        return new ResponseEntity<>(memberSignupResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid MemberLoginDto loginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(loginDto.getEmail());
        // email이 없을 경우 Exception이 발생한다. Global Exception에 대한 처리가 필요하다.
        Member member = memberService.getByEmail(loginDto.getEmail());

        //해당 유저가 없거나, 비밀번호가 틀리거나 roleNo가 1 or 2가 아닐때
        if (member == null || !passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        List<String> roles = member.getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());

        // JWT토큰을 생성하였다. jwt라이브러리를 이용하여 생성.
        String accessToken = jwtTokenizer.createAccessToken(member.getMemberNo(), member.getEmail(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getMemberNo(), member.getEmail(), roles);

        // RefreshToken을 DB에 저장한다. 성능 때문에 DB가 아니라 Redis에 저장하는것이 더 좋다.
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setMemberNo(member.getMemberNo());
        refreshTokenService.addRefreshToken(refreshTokenEntity);

        MemberLoginResponseDto loginResponse = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberNo(member.getMemberNo())
                .name(member.getName())
                .build();
        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestBody RefreshTokenDto refreshTokenDto) {
        refreshTokenService.deleteRefreshToken(refreshTokenDto.getRefreshToken());
        // token repository에서 refresh Token에 해당하는 값을 삭제한다.
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity requestRefresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenDto.getRefreshToken());
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());
        Long memberNo = Long.valueOf((Integer) claims.get("userId"));

        Member member = memberService.getMemberBy(memberNo);

        List roles = (List) claims.get("roles");
        String email = claims.getSubject();

        String accessToken = jwtTokenizer.createAccessToken(memberNo, email, roles);

        MemberLoginResponseDto loginResponse = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshTokenDto.getRefreshToken())
                .memberNo(member.getMemberNo())
                .name(member.getName())
                .build();
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }


}