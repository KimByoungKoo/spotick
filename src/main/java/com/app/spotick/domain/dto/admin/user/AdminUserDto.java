package com.app.spotick.domain.dto.admin.user;

import com.app.spotick.domain.type.user.AuthorityType;
import com.app.spotick.domain.type.user.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdminUserDto {
    private Long id;
    private String email;
    private String nickName;
    private String tel;
    private String userStatus;
    private String authorityType;

    public AdminUserDto(Long id, String email, String nickName, String tel, String userStatus, String authorityType) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.tel = tel;
        this.userStatus = userStatus;
        this.authorityType = authorityType;
    }

}
