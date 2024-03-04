package com.app.spotick.repository.admin.user;

import com.app.spotick.domain.entity.user.QUser;
import com.app.spotick.domain.entity.user.QUserAuthority;
import com.app.spotick.domain.entity.user.User;
import com.app.spotick.domain.entity.user.UserAuthority;
import com.app.spotick.domain.type.user.UserStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static com.app.spotick.domain.entity.user.QUser.*;
import static com.app.spotick.domain.entity.user.QUserAuthority.userAuthority;

@SpringBootTest
@Transactional
@Commit
class AdminUserRepositoryTest {

    @Autowired
    AdminUserRepository adminUserRepository;

    @PersistenceContext
    EntityManager entityManager;

    @BeforeEach
    void setUp(){
        User user1 = User.builder()
                .email("aaa@naver.com")
                .password("1234")
                .nickName("김철수")
                .tel("01011111111")
                .userStatus(UserStatus.ACTIVATE)
                .build();

        User user2 = User.builder()
                .email("bbb@naver.com")
                .password("1111")
                .nickName("박웅이")
                .tel("01022222222")
                .userStatus(UserStatus.SUSPENDED_7_DAYS)
                .build();

        User user3 = User.builder()
                .email("ccc@naver.com")
                .password("2222")
                .nickName("김영희")
                .tel("01033333333")
                .userStatus(UserStatus.ACTIVATE)
                .build();

        User user4 = User.builder()
                .email("ddd@naver.com")
                .password("3333")
                .nickName("홍길동")
                .tel("01044444444")
                .userStatus(UserStatus.SUSPENDED_30_DAYS)
                .build();

        User user5 = User.builder()
                .email("eee@naver.com")
                .password("1111")
                .nickName("신짱구")
                .tel("01055555555")
                .userStatus(UserStatus.PERMANENTLY_SUSPENDED)
                .build();

        User user6 = User.builder()
                .email("fff@naver.com")
                .password("1111")
                .nickName("람머스")
                .tel("0106666666")
                .userStatus(UserStatus.DEACTIVATE)
                .build();

    }
}