package com.app.spotick.domain.dto.place.review;

import com.app.spotick.domain.dto.place.file.PlaceFileDto;
import com.app.spotick.domain.embedded.post.PostAddress;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ContractedPlaceDto {
    private Long id;
    private String title;
    private Integer price;
    private PostAddress placeAddress;
    private PlaceFileDto placeFileDto;
    private double evalAvg;
    private Long evalCount;
    private Long bookmarkCount;

    public ContractedPlaceDto(Long id, String title, Integer price, PostAddress placeAddress, PlaceFileDto placeFileDto, double evalAvg, Long evalCount, Long bookmarkCount) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.placeAddress = placeAddress;
        this.placeFileDto = placeFileDto;
        this.evalAvg = evalAvg;
        this.evalCount = evalCount;
        this.bookmarkCount = bookmarkCount;
    }
}
