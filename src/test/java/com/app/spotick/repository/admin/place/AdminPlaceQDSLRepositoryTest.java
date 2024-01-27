package com.app.spotick.repository.admin.place;

import com.app.spotick.domain.dto.admin.place.AdminPlaceListDto;
import com.app.spotick.domain.embedded.post.PostAddress;
import com.app.spotick.domain.entity.place.Place;
import com.app.spotick.domain.entity.user.User;
import com.app.spotick.repository.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Commit
class AdminPlaceQDSLRepositoryTest {

    @Autowired
    AdminPlaceRepository adminPlaceRepository;
    @Autowired
    UserRepository userRepository;
    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void setUp(){
        User user =  User.builder()
                .email("aaa@naver.com")
                .nickName("김철수")
                .password("1234")
                .tel("01011111111")
                .build();

        em.persist(user);

        Place place1 = Place.builder()
                .subTitle("test")
                .info("content")
                .placeAddress(new PostAddress("강남구", "논현동"))
                .price(10_000)
                .rule("rule1")
                .accountNumber("11111111")
                .accountHolder("김철수")
                .bankName("카카오 뱅크")
                .build();

        Place place2 = Place.builder()
                .subTitle("test")
                .info("content")
                .placeAddress(new PostAddress("강남구", "논현동"))
                .price(10_000)
                .rule("rule1")
                .accountNumber("11111111")
                .accountHolder("김철수")
                .bankName("카카오 뱅크")
                .build();

        Place place3 = Place.builder()
                .subTitle("test")
                .info("content")
                .placeAddress(new PostAddress("강남구", "논현동"))
                .price(10_000)
                .rule("rule1")
                .accountNumber("11111111")
                .accountHolder("김철수")
                .bankName("카카오 뱅크")
                .build();

        em.persist(place1);
        em.persist(place2);
        em.persist(place3);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("관리자 장소글 페이징 처리 단위 테스트")
    void placeListWithSlice() {
        PageRequest pageInfo = PageRequest.of(0, 10);


        Slice<AdminPlaceListDto> adminPlace = adminPlaceRepository.placeListWithSlice(pageInfo);
        adminPlace.getContent().forEach(System.out::println);

        System.out.println(adminPlace.hasNext());
    }
}