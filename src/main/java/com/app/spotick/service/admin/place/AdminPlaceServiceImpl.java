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

    @Override
    @Transactional(readOnly = true)
    public Slice<AdminPlaceListDto> placeListWithSlice( Pageable pageable){
        Slice<AdminPlaceListDto> userPlaceList = adminPlaceRepository.placeListWithSlice(pageable);

        return userPlaceList;
    }

    @Override
    public AdminPlaceListDto modifyPlaceStatus(AdminPlaceListDto adminPlaceListDto){
        Place userPlaceStatus = adminPlaceRepository.findById(adminPlaceListDto.getPlaceId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 장소글입니다."));

        return null;
    }
}
