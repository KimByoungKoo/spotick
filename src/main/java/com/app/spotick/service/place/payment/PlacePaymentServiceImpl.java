package com.app.spotick.service.place.payment;

import com.app.spotick.api.dto.place.PlacePaymentDto;
import com.app.spotick.api.dto.response.CommonResponse;
import com.app.spotick.domain.entity.place.PlacePayment;
import com.app.spotick.domain.entity.place.PlaceReservation;
import com.app.spotick.domain.entity.user.User;
import com.app.spotick.domain.type.payment.PaymentStatus;
import com.app.spotick.repository.place.payment.PlacePaymentRepository;
import com.app.spotick.repository.place.reservation.PlaceReservationRepository;
import com.app.spotick.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PlacePaymentServiceImpl implements PlacePaymentService {
    private final PlacePaymentRepository placePaymentRepository;
    private final UserRepository userRepository;
    private final PlaceReservationRepository placeReservationRepository;

    @Override
    public ResponseEntity<CommonResponse<Long>> savePayment(Long userId, PlacePaymentDto dto) {
        User tmpUser = userRepository.getReferenceById(userId);

        PlaceReservation foundReservation = placeReservationRepository.findByIdAndUser(
                dto.getSave().getReservationId(),
                tmpUser
        ).orElseThrow(
                NoSuchElementException::new
        );

        PlacePayment paymentEntity = PlacePayment.builder()
                .amount(foundReservation.getAmount())
                .paymentMethod(dto.getSave().getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .placeReservation(foundReservation)
                .user(tmpUser)
                .build();

        PlacePayment savedEntity = placePaymentRepository.save(paymentEntity);

        return new ResponseEntity<>(
                CommonResponse.<Long>builder()
                        .success(true)
                        .message("주문 저장 성공")
                        .data(savedEntity.getId())
                        .build(),
                HttpStatus.OK);
    }
}
