package com.app.spotick.repository.admin.place;

import com.app.spotick.domain.dto.admin.place.AdminPlaceListDto;
import com.app.spotick.domain.entity.place.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface AdminPlaceQDSLRepository {

//  관리자 페이지 장소글 전체 조회 및 페이징 처리(무한 스크롤)
    Slice<AdminPlaceListDto> placeListWithSlice(Pageable pageable);

//  관리자 페이지 장소글 이메일로 조회
    Slice<AdminPlaceListDto> placeListEmailWithSlice(String email, Pageable pageable);

//  관리자 페이지 장소글 닉네임으로 조회하기
    Slice<AdminPlaceListDto> placeListNickNameWithSlice(String nickName, Pageable pageable);

//  관리자 페이지 장소글 작성일로 조회하기
//    Slice<AdminPlaceListDto> placeListDateWithSlice(String createdDate, Pageable pageable);

//  관리자 페이지 장소글 상태로 조회하기
}
