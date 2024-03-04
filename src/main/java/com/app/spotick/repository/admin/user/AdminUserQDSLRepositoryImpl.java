package com.app.spotick.repository.admin.user;

import com.app.spotick.domain.dto.admin.user.AdminUserListDto;
import com.app.spotick.domain.entity.user.QUserAuthority;
import com.app.spotick.domain.entity.user.UserAuthority;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDateTime;
import java.util.List;

import static com.app.spotick.domain.entity.user.QUser.user;

@RequiredArgsConstructor
public class AdminUserQDSLRepositoryImpl implements AdminUserQDSLRepository {

    private final JPAQueryFactory queryFactory;

    //  관리자 페이지 회원 관리 회원 전체 리스트 조회 및 페이징 처리(무한 스크롤)
    @Override
    public Slice<AdminUserListDto> userListWithSlice(String keyword, Pageable pageable) {
        String searchType = ""; // keyword
        StringPath searchTypeValue = null; // 검색조건

        switch (searchType) {
            case "email":
                searchTypeValue = user.email;
                break;
            case "nickname":
                searchTypeValue = user.nickName;
                break;
        }

        StringPath nickName = user.nickName;
        List<AdminUserListDto> userList = queryFactory.select(
                Projections.constructor(AdminUserListDto.class,
                        user.id,
                        user.email,
                        user.nickName,
                        user.userStatus,
                        user.tel,
                        user.createdDate,
                        user.suspensionEndDate
                )
        ).from(user)
                .where(searchTypeValue.contains(keyword),
                        user.createdDate.eq(LocalDateTime.now()))
                .orderBy(user.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;

        if (userList.size() > pageable.getPageSize()) {
            userList.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(userList, pageable, hasNext);
    }

}
