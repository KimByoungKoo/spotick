package com.app.spotick.domain.dto.admin.user;

import com.app.spotick.domain.entity.user.User;
import com.app.spotick.domain.entity.user.UserAuthority;
import com.app.spotick.domain.type.user.AuthorityType;
import com.app.spotick.domain.type.user.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
public class AdminUserListDto {
    private Long id;
    private String email;
    private String nickName;
    private String tel;
    private LocalDateTime createdDate;
    private UserStatus userStatus;
    private LocalDate suspensionEndDate;
    private AuthorityType authorityType;

    //  가입일자 형식 바꾸기
    public String getCreatedDate(){
        return createdDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
    
}
