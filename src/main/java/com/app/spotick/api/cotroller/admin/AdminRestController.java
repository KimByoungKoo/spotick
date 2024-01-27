package com.app.spotick.api.cotroller.admin;

import com.app.spotick.domain.dto.admin.place.AdminPlaceListDto;
import com.app.spotick.domain.type.post.PostStatus;
import com.app.spotick.service.admin.place.AdminPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminRestController {
    private final AdminPlaceService adminPlaceService;

    @GetMapping("/place/list")
    public void adminPlaceList(Pageable pageable){
        Slice<AdminPlaceListDto> adminPlaceListDtos = adminPlaceService.placeListWithSlice(pageable);

        PostStatus[] values = PostStatus.values();

        // map 또는 2024-01-27 수업 영상에 들은 common api 참조
    }




}
