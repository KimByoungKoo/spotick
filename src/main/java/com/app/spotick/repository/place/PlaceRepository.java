package com.app.spotick.repository.place;

import com.app.spotick.domain.dto.place.PlaceDto;
import com.app.spotick.domain.dto.place.reservation.PlaceReserveBasicInfoDto;
import com.app.spotick.domain.entity.place.Place;
import com.app.spotick.domain.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long>,PlaceQDSLRepository {

    Optional<Place> findByIdAndUser(Long id, User user);

}
