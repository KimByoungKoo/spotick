package com.app.spotick.repository.admin.place;

import com.app.spotick.domain.entity.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminPlaceRepository extends JpaRepository<Place, Long>, AdminPlaceQDSLRepository {
//  전체 장소 게시글 조회

//  닉네임으로 장소 게시글 조회

//  검색조건 장소, 티켓의 따른 게시글 전체 조회

//  장소 게시글 작성일로 조회

//  장소 게시글 상태에 따른 조회(공개, 비공개)
}
