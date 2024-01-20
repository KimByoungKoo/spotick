package com.app.spotick.domain.entity.user;

import com.app.spotick.domain.base.image.ImageBase;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity @Table(name = "TBL_USER_PROFILE_FILE")
@SequenceGenerator(name = "SEQ_USER_PROFILE_FILE_GENERATOR", sequenceName = "SEQ_USER_PROFILE_FILE",allocationSize = 1)
@Getter @Setter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileFile extends ImageBase {
    @Id @GeneratedValue(generator = "SEQ_USER_PROFILE_FILE_GENERATOR")
    @Column(name = "USER_PROFILE_FILE_ID")
    private Long id;


    private boolean isDefaultImage = true;

    @Builder
    public UserProfileFile(String fileName, String uuid, String uploadPath, Long id, boolean isDefaultImage) {
        super(fileName, uuid, uploadPath);
        this.id = id;
        this.isDefaultImage = isDefaultImage;
    }
}











