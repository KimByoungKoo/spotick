package com.app.spotick.controller.placeBookmark;

import com.app.spotick.domain.dto.user.UserDetailsDto;
import com.app.spotick.service.place.PlaceBookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkController {
    private final PlaceBookmarkService placeBookmarkService;

    /*
        북마크를 등록 혹은 삭제하는 쿼리문이며 결과값은
        등록될 시 => true 반환
        삭제될 시 => false 반환
     */
    @GetMapping("/bookmark")
    public boolean bookmark(@RequestParam("placeId") Long placeId,
                         @AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        return placeBookmarkService.bookmark(placeId, userDetailsDto.getId());
    }

    /*
        해당 게시물이 북마크가 되어있는 지 확인하는 용도이며 결과값은
        등록되어있을 시 => true 반환
        삭제될 시 => false 반환
     */
    @GetMapping("/bookmarkCheck")
    public boolean bookmarkCheck(@RequestParam("placeId") Long placeId,
                              @AuthenticationPrincipal UserDetailsDto userDetailsDto) {
        return placeBookmarkService.bookmarkCheck(placeId, userDetailsDto.getId());
    }
}
