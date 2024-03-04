package com.app.spotick.service.admin.user;

import com.app.spotick.domain.dto.admin.user.AdminUserListDto;
import com.app.spotick.repository.admin.user.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepository adminUserRepository;

//  관리자 페이지 회원 관리 회원 조회 및 페이징 처리(무한 스크롤)
    @Override
    public Slice<AdminUserListDto> userListWithSlice(String keyword, Pageable pageable) {
        Slice<AdminUserListDto> adminUserList = adminUserRepository.userListWithSlice(keyword, pageable);

        return adminUserList;
    }
}
