package com.triple.travelerclubservice.service;

import com.triple.travelerclubservice.dto.ReviewPointResponse;

public interface UsersService {
    ReviewPointResponse getReviewPoints(String userId);
}
