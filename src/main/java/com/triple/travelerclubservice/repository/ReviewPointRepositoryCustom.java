package com.triple.travelerclubservice.repository;

import com.triple.travelerclubservice.entity.ReviewPoints;

import java.util.List;

public interface ReviewPointRepositoryCustom {

    List<ReviewPoints> findByReviewId(String reviewId);

}
