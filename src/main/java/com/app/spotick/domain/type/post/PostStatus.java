package com.app.spotick.domain.type.post;

import lombok.Getter;

@Getter
public enum PostStatus {
    REGISTRATION_PENDING("등록 대기중"), // 등록 대기중
    APPROVED("활성"),             // 승인
    REJECTED("거절"),             // 거절
    REPLACED("대체됨"),             // 대체됨
    MODIFICATION_PENDING("수정 대기중"),  // 수정 대기중
    DISABLED("비활성")    //비활성화(호스트가 임의로 중단할 때)
    ;

    private final String displayName;
    PostStatus(String displayName) {
        this.displayName = displayName;
    }


}
