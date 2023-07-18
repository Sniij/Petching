package com.Petching.petching.user.entity;

import com.Petching.petching.audit.Auditable;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Builder
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "USERS")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 15, unique = true)
    private String nickName;

    @Column
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;


    public void passwordEncode (PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

//    @Enumerated(EnumType.STRING)
//    private Role role;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

//    public void authorizeUser () {
//        this.role = Role.USER;
//    }

    public void updateNickName (String nickName) {
        this.nickName = nickName;
    }
    public void updateEmail (String email) {
        this.email = email;
    }
    public void updatePassword (String password) {
        this.password = password;
    }
    public void updateAddress (String address) {
        this.address = address;
    }
}