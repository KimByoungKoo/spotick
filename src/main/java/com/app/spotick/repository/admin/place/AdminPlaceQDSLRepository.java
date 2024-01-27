package com.app.spotick.repository.admin.place;

import com.app.spotick.domain.dto.admin.place.AdminPlaceListDto;
import com.app.spotick.domain.entity.place.Place;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface AdminPlaceQDSLRepository {

//  관리자 페이지 장소 게시글 페이징 처리(무한 스크롤)
    Slice<AdminPlaceListDto> placeListWithSlice(Pageable pageable);
}
