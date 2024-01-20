package com.app.spotick.repository.user;

import com.app.spotick.domain.dto.user.UserProfileDto;
import com.app.spotick.domain.entity.user.User;
import com.app.spotick.domain.entity.user.UserAuthority;
import com.app.spotick.domain.entity.user.UserProfileFile;
import com.app.spotick.domain.type.user.AuthorityType;
import com.app.spotick.domain.type.user.UserStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Commit
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAuthorityRepository authorityRepository;

    @PersistenceContext
    private EntityManager em;

    private User user;
    private UserProfileFile file;

    @BeforeEach
    void setUp() {
        file = UserProfileFile.builder()
                .uploadPath("test")
                .fileName("testName")
                .uuid("12345678")
                .isDefaultImage(false)
                .build();

        user = User.builder()
                .id(1L)
                .email("aaa")
                .password("1234")
                .nickName("홍길동")
                .tel("0101111111")
                .userStatus(UserStatus.ACTIVATE)
                .userProfileFile(file)
                .build();

        userRepository.save(user);

        authorityRepository.save(UserAuthority.builder()
                .user(user)
                .authorityType(AuthorityType.ROLE_USER)
                .build());

        em.flush();
        em.clear();
    }


    @Test
    @DisplayName("이메일로 회원정보 가져오기")
    void findUserByEmailTest() {
        User foundUser = userRepository.findUserAndProfileByEmail(user.getEmail());
        assertThat(foundUser).isNotNull().extracting("id")
                .isNotNull();
        System.out.println("foundUser = " + foundUser);
    }

    @Test
    @DisplayName("회원으로 회원의 권한 목록들 가져오기")
    void findUserAuthoritiesTest() {
        User foundUser = userRepository.findById(user.getId()).orElseThrow();

        List<UserAuthority> userAuthorityByUser = authorityRepository.findUserAuthorityByUser(foundUser);

        System.out.println("userAuthorityByUser = " + userAuthorityByUser);
    }


    @Test
    @DisplayName("회원 프로필 정보 테스트")
    void findUserProfileById() {
        UserProfileDto userProfileDto = userRepository.findUserProfileById(user.getId()).get();

        assertThat(userProfileDto).isNotNull();
        System.out.println("userProfileDto = " + userProfileDto);
    }

}