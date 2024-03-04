package com.app.spotick.repository.admin.place;

import com.app.spotick.domain.dto.admin.place.AdminPlaceListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.app.spotick.domain.entity.place.QPlace.place;
import static com.app.spotick.domain.entity.user.QUser.user;

@RequiredArgsConstructor
public class AdminPlaceQDSLRepositoryImpl implements AdminPlaceQDSLRepository {

    private final JPAQueryFactory queryFactory;

//  관리자 페이지 장소글 전체 조회 및 페이징 처리(무한 스크롤)
    @Override
    public Slice<AdminPlaceListDto> placeListWithSlice(Pageable pageable) {

        List<AdminPlaceListDto> placeList = queryFactory.select(
                Projections.constructor(AdminPlaceListDto.class,
                        place.id,
                        place.title,
                        user.id,
                        user.email,
                        user.nickName,
                        place.createdDate,
                        place.placeStatus
                )
        )
                .from(place)
                .join(place.user, user)
                .where(place.user.email.eq(user.email))
                .orderBy(place.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();


        boolean hasNext = false;

        if(placeList.size() > pageable.getPageSize()){
            placeList.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(placeList, pageable, hasNext);
    }

//  관리자 페이지 장소글 회원 이메일로 조회 및 페이징 처리(무한 스크롤)
    @Override
    public Slice<AdminPlaceListDto> placeListEmailWithSlice(String email, Pageable pageable) {
        List<AdminPlaceListDto> placeEmailList = queryFactory.select(
                Projections.constructor(
                        AdminPlaceListDto.class,
                        place.id,
                        place.title,
                        user.id,
                        user.email,
                        user.nickName,
                        place.createdDate,
                        place.placeStatus
                )
        )
                .from(place)
                .join(place.user, user)
                .orderBy(place.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;

        if(placeEmailList.size() > pageable.getPageSize()){
            placeEmailList.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(placeEmailList, pageable, hasNext);
    }

//  관리자 페이지 장소글 회원 닉네임으로 조회 및 페이징 처리(무한 스크롤)
    @Override
    public Slice<AdminPlaceListDto> placeListNickNameWithSlice(String nickName, Pageable pageable) {

        List<AdminPlaceListDto> placeNickNameList = queryFactory.select(
                Projections.constructor(
                        AdminPlaceListDto.class,
                        place.id,
                        place.title,
                        user.id,
                        user.email,
                        user.nickName,
                        place.createdDate,
                        place.placeStatus
                )
        )
                .from(place)
                .join(place.user, user)
                .where(place.user.nickName.eq(user.nickName))
                .orderBy(place.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = false;

        if(placeNickNameList.size() > pageable.getPageSize()){
            placeNickNameList.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(placeNickNameList, pageable, hasNext);
    }
}
