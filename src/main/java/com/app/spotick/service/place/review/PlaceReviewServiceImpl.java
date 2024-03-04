package com.app.spotick.service.place.review;

import com.app.spotick.domain.dto.place.review.PlaceReviewListDto;
import com.app.spotick.domain.dto.place.review.PlaceReviewRegisterDto;
import com.app.spotick.domain.dto.place.review.PlaceReviewUpdateDto;
import com.app.spotick.domain.entity.place.PlaceReservation;
import com.app.spotick.domain.entity.place.PlaceReview;
import com.app.spotick.domain.entity.user.User;
import com.app.spotick.repository.place.Review.PlaceReviewRepository;
import com.app.spotick.repository.place.reservation.PlaceReservationRepository;
import com.app.spotick.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlaceReviewServiceImpl implements PlaceReviewService {
    private final PlaceReviewRepository placeReviewRepository;
    private final UserRepository userRepository;
    private final PlaceReservationRepository placeReservationRepository;

    @Override
    public void registerReview(PlaceReviewRegisterDto placeReviewRegisterDto) {
        User tmpUser = userRepository.getReferenceById(placeReviewRegisterDto.getUserId());
        PlaceReservation tmpReservation
                = placeReservationRepository.getReferenceById(placeReviewRegisterDto.getReservationId());

        PlaceReview placeReview = PlaceReview.builder()
                .user(tmpUser)
                .placeReservation(tmpReservation)
                .score(placeReviewRegisterDto.getScore())
                .content(placeReviewRegisterDto.getContent())
                .build();
        placeReviewRepository.save(placeReview);
    }

    @Override
    public void updateReview(PlaceReviewUpdateDto placeReviewUpdateDto) {
        PlaceReview foundReview = placeReviewRepository.findById(placeReviewUpdateDto.getReviewId()).orElseThrow(
                NoSuchElementException::new
        );

        foundReview.updateReview(placeReviewUpdateDto.getContent(), placeReviewUpdateDto.getScore());
    }

    @Override
    public Optional<PlaceReview> findReview(Long reviewId, Long userId) {
        User tmpUser = userRepository.getReferenceById(userId);

        return placeReviewRepository.findByIdAndUser(reviewId, tmpUser);
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<PlaceReviewListDto> findPlaceReviewSlice(Long placeId, Pageable pageable) {
        return placeReviewRepository.findReviewSliceByPlaceId(placeId,pageable);
    }
}
