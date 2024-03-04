package com.app.spotick.domain.dto.user;

import com.app.spotick.domain.entity.user.User;
import com.app.spotick.domain.entity.user.UserProfileFile;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data @NoArgsConstructor
public class UserJoinDto {
    private Long id;
    private String email;
    private String password;
    private String nickName;
    private String tel;

    public UserJoinDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickName = user.getNickName();
        this.tel = user.getTel();
    }

    public User toEntity(UserProfileFile userProfileFile){
        return User.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickName(nickName)
                .tel(tel)
                .userProfileFile(userProfileFile)
                .build();
    }

    public static UserJoinDto fromOAuth2ByEmailNickname(String email,String nickname) {
        UserJoinDto userJoinDto = new UserJoinDto();
        String uuid = UUID.randomUUID().toString();
        userJoinDto.setEmail(email);
        userJoinDto.setNickName(nickname+uuid.substring(0,uuid.indexOf('-')));
        userJoinDto.setPassword(uuid);

        return userJoinDto;
    }



}
