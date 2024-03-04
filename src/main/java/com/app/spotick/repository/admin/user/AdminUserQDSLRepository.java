package com.app.spotick.repository.admin.user;

import com.app.spotick.domain.dto.admin.user.AdminUserListDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface AdminUserQDSLRepository {

//  관리자 페이지 회원 관리 회원 조회 및 페이징 처리(무한 스크롤)
    Slice<AdminUserListDto> userListWithSlice(String keyword,  Pageable pageable);

}
