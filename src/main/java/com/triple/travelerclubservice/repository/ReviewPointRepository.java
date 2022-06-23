package com.triple.travelerclubservice.repository;

import com.triple.travelerclubservice.entity.ReviewPoints;
import com.triple.travelerclubservice.enumeration.ReviewPointState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewPointRepository extends JpaRepository<ReviewPoints, Long>, ReviewPointRepositoryCustom {

    boolean existsByUserIdAndPlaceId(String userId, String placeId);

    boolean existsByPlaceIdAndState(String placeId, ReviewPointState state);

}
