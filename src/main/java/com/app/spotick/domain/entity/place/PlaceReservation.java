package com.app.spotick.domain.entity.place;

import com.app.spotick.domain.base.Period;
import com.app.spotick.domain.entity.user.User;
import com.app.spotick.domain.type.place.PlaceReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name = "TBL_PLACE_RESERVATION")
@SequenceGenerator(name = "SEQ_PLACE_RESERVATION_GENERATOR", sequenceName = "SEQ_PLACE_RESERVATION",allocationSize = 1)
@Getter @ToString(callSuper = true) @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceReservation extends Period {
    @Id @GeneratedValue(generator = "SEQ_PLACE_RESERVATION_GENERATOR")
    @Column(name = "PLACE_RESERVATION_ID")
    private Long id;
    private Integer visitors;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Integer amount;
    @Column(length = 1000)
    private String content; // 예약 전달 내용
    // 리뷰 작성 거절 여부 체크: true 시 리뷰 쓰기 거절, false일 시 리뷰 작성 가능
    // (예약 생성시 false로 설정 할 것. // 리뷰쓰기 거절 시 true로 전환 할 것)
    private boolean notReviewing;
    @Enumerated(EnumType.STRING)
    private PlaceReservationStatus reservationStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLACE_ID")
    private Place place;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne(mappedBy = "placeReservation",fetch = FetchType.LAZY)
    private PlaceReview placeReview;

    @Builder
    public PlaceReservation(Long id, Integer visitors, LocalDateTime checkIn, LocalDateTime checkOut, Integer amount, String content, boolean notReviewing, PlaceReservationStatus reservationStatus, Place place, User user) {
        this.id = id;
        this.visitors = visitors;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.amount = amount;
        this.content = content;
        this.notReviewing = notReviewing;
        this.reservationStatus = reservationStatus;
        this.place = place;
        this.user = user;
    }

    public void updateStatus(PlaceReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void updateNotReviewing(boolean notReviewing) {
        this.notReviewing = notReviewing;
    }
}
