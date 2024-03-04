package com.app.spotick.domain.type.user;

import lombok.Getter;

@Getter
public enum AuthorityType {
    ROLE_USER("일반회원"),
    ROLE_ADMIN("관리자");

    private final String displayName;
//  권한
    private static final String PREFIX ="ROLE_";

    AuthorityType(String displayName) {
        this.displayName = displayName;
    }

//   권한
    public String getSecurityRole(){

        return PREFIX + name();
    }

}
