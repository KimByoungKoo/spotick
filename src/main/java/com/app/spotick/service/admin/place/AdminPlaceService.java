package com.app.spotick.service.admin.place;

import com.app.spotick.domain.dto.admin.place.AdminPlaceListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface AdminPlaceService {

//  관리자 페이지 장소글 페이징 처리(무한 스크롤)
    Slice<AdminPlaceListDto> placeListWithSlice(Pageable pageable);

//  관리자 페이지 장소글 이메일로 조회 및 페이징 처리(무한 스크롤)
    Slice<AdminPlaceListDto> placeListEmailSlice(String email, Pageable pageable);

//  관리자 페이지 장소글 상태 변경 -> String으로 반환타입 바꾸고 id를.. 받는다..
    AdminPlaceListDto modifyPlaceStatus(AdminPlaceListDto adminPlaceListDto);
}
