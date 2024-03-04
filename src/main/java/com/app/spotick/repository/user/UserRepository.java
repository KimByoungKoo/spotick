package com.app.spotick.repository.user;

import com.app.spotick.api.dto.user.UserFindEmailDto;
import com.app.spotick.domain.dto.user.UserProfileDto;
import com.app.spotick.domain.entity.user.User;
import com.app.spotick.domain.entity.user.UserProfileFile;
import org.hibernate.annotations.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            select u from User u join fetch u.userProfileFile
            where u.email = :email
            """)
    Optional<User> findUserAndProfileByEmail(@Param("email") String email);
    Optional<User> findUserByEmail(String email);

    //    프로필 사진 및 유저 정보 조회(비밀번호 제외)
    @Query("select new com.app.spotick.domain.dto.user.UserProfileDto(" +
            "u.id, u.email, u.nickName, u.tel, u.userStatus, f.createdDate, f.fileName, f.uuid, f.uploadPath, f.isDefaultImage" +
            ") " +
            "from User u join u.userProfileFile f where u.id = :id")
    Optional<UserProfileDto> findUserProfileById(@Param("id") Long id);

    @Query("""
            select u.userProfileFile from User u join u.userProfileFile uf
            where u.id = :userId
            """)
    UserProfileFile findUserProfileFileByUserId(@Param("userId")Long userId);

//    이메일 중복 확인
    boolean existsUserByEmail(String email);

//    닉네임 중복 확인
    boolean existsUserByNickName(String nickname);

//    닉네임 전화번호일치 여부 확인(아이디찾기
    boolean existsUserByNickNameAndTel(String nickname, String tel);


//    jpql에서 dto반환 내부클래스에 접근할 때는 . 대신 $ 사용
    @Query("""
            select new com.app.spotick.api.dto.user.UserFindEmailDto$Response(
                u.email, u.createdDate
            )
            from User u
            where u.nickName = :nickname and u.tel = :tel        
            """)
    Optional<UserFindEmailDto.Response> findEmailDtoByNickNameAndTel(@Param("nickname")String nickname, @Param("tel")String tel);

}




