package com.app.spotick.domain.dto.admin.place;

import com.app.spotick.domain.base.Period;
import com.app.spotick.domain.entity.place.Place;
import com.app.spotick.domain.entity.user.User;
import com.app.spotick.domain.type.post.PostStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class AdminPlaceListDto {
    private Long placeId;
    private String title;
    private Long userId;
    private String email;
    private String nickName;
    private LocalDateTime createdDate;
    private PostStatus placeStatus;

    public AdminPlaceListDto(Long placeId, String title, Long userId, String email, String nickName, LocalDateTime createdDate, PostStatus placeStatus) {
        this.placeId = placeId;
        this.title = title;
        this.userId = userId;
        this.email = email;
        this.nickName = nickName;
        this.createdDate = createdDate;
        this.placeStatus = placeStatus;
    }

//    public Place toEntity(){
//        return Place.builder()
//                .id(placeId)
//                .subTitle(subTitle)
//                .user(User.builder().id(this.userId).build())
//                .placeStatus(PostStatus.valueOf(PostStatus.class, this.placeStatus))
//                .build();
//    }

//  장소글 작성일자 형식 바꾸기
    public String getCreatedDate(){
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }


}
