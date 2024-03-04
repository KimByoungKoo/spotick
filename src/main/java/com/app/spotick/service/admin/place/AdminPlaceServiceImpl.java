package com.app.spotick.service.admin.place;

import com.app.spotick.domain.dto.admin.place.AdminPlaceListDto;
import com.app.spotick.domain.entity.place.Place;
import com.app.spotick.repository.admin.place.AdminPlaceRepository;
import com.app.spotick.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminPlaceServiceImpl implements AdminPlaceService{

    private final AdminPlaceRepository adminPlaceRepository;
    private final UserRepository userRepository;

//  관리자 페이지 장소글 리스트 조회 및 페이징 처리(무한 스크롤)
    @Override
    @Transactional(readOnly = true)
    public Slice<AdminPlaceListDto> placeListWithSlice(Pageable pageable){
        Slice<AdminPlaceListDto> userPlaceList = adminPlaceRepository.placeListWithSlice(pageable);

        return userPlaceList;
    }

//  관리자 페이지 장소글 이메일로 조회 및 페이징 처리(무한 스크롤)
    @Override
    public Slice<AdminPlaceListDto> placeListEmailSlice(String email, Pageable pageable) {
        return null;
    }

    @Override
    public AdminPlaceListDto modifyPlaceStatus(AdminPlaceListDto adminPlaceListDto){
        Place userPlaceStatus = adminPlaceRepository.findById(adminPlaceListDto.getPlaceId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 장소글입니다."));

        return null;
    }
}
