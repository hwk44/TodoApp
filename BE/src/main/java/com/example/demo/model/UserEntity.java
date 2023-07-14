package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id; // 유저고유 아이디

    // OAuth 를 이용해 SSO(Single Sign On) 을 구현해야 할 경우 password 가 필요 없음
    // 컨트롤러에서 password 를 입력하도록 설정 가능
    @Column(nullable = false)
    private String username; // 아이디로 사용할 유저네일. 이메일 or 문자열
    private String password; // password
    private String role; // 사용자 어드민, 일반 유저
    private String authProvider; // OAuth 에서 사용할 유저 정보 제공자 책에서는 github

}
