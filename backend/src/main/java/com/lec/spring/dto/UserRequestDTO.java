package com.lec.spring.dto;

import com.lec.spring.domain.Auth;
import com.lec.spring.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String phone_number;
    private String nickname;
    private String email;
    private Double star;
    private Auth auth;
    private String certification;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .username(this.username)
                .password(passwordEncoder.encode(this.password)) // 비밀번호 인코딩
                .name(this.name)
                .phone_number(this.phone_number)
                .nickname(this.nickname)
                .email(this.email)
                .star(this.star)
                .auth(this.auth)
                .certification(this.certification)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
