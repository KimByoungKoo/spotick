package com.app.spotick.repository.admin.user;

import com.app.spotick.domain.entity.user.User;
import com.app.spotick.domain.entity.user.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<User, Long>, AdminUserQDSLRepository{

//  관리자 페이지 회원 관리 회원 조회 및 페이징 처리

//  관리자 페이지 회원 관리 회원 등급 조회하기
    @Query("select ua.authorityType from UserAuthority ua " +
            "join User u on ua.id = u.id")
    Optional<UserAuthority> findAuthorityByName();

//  관리자 페이지 회원 관리 회원 이메일로 조회하기(단건 조회)
    @Query("select u.email, u.nickName, u.userStatus, u.tel, u.createdDate, u.suspensionEndDate " +
            "from User u join UserAuthority ua on u.id = ua.id " +
            "where u.email = :email")
    Optional<User> findUserByName(@Param("email") String email);

//  관리자 페이지 회원 관리 회원등급으로 조회하기 페이징 처리(무한 스크롤)
    @Query("select u.email, u.nickName, u.userStatus, u.tel, u.createdDate, u.suspensionEndDate " +
            "from User u join UserAuthority ua on u.id = ua.id " +
            "where ua.authorityType = :authorityType")
    List<User> findUserByAuthority(@Param("authorityType") String authorityType);

//  관리자 페이지 회원 관리 회원 가입날로 조회하기 -> Dto로 조회

}
