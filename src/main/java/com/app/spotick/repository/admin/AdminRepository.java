package com.app.spotick.repository.admin;

import com.app.spotick.domain.dto.admin.AdminUserDto;
import com.app.spotick.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<User, Long> {

//  회원 전체 조회하기
    List<AdminUserDto> findAllUser();

//  회원 이메일로 조회하기(단건 조회)
    @Query("select u.email, u.tel, u.createdDate, u.userStatus from User u " +
            "where u.email = :email")
    Optional<User> findUserByName(String name);

//  회원등급으로 조회하기
    List<AdminUserDto> findAuthorityUser();

//  회원 가입날로 조회하기 -> Dto로 조회
    @Query("select u.email, u.tel, u.createdDate, u.userStatus from User u " +
            "where u.createdDate = :createDate")
    List<AdminUserDto> findUserByDate();
}
