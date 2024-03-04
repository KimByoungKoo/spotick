package com.app.spotick.repository.place.inquiry;

import com.app.spotick.domain.entity.place.Place;
import com.app.spotick.domain.entity.place.PlaceInquiry;
import com.app.spotick.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceInquiryRepository extends JpaRepository<PlaceInquiry, Long>, PlaceInquiryQDSLRepository {

    Optional<PlaceInquiry> findByIdAndUser(Long id, User user);

    Optional<PlaceInquiry> findByIdAndPlace(Long id, Place place);
}
