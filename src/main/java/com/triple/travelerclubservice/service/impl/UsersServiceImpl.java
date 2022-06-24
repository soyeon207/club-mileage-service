package com.triple.travelerclubservice.service.impl;

import com.triple.travelerclubservice.dto.ReviewPointResponse;
import com.triple.travelerclubservice.mapper.ReviewPointWithHistoryDtoMapper;
import com.triple.travelerclubservice.repository.ReviewPointRepository;
import com.triple.travelerclubservice.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final ReviewPointRepository reviewPointRepository;
    private final ReviewPointWithHistoryDtoMapper reviewPointWithHistoryDtoMapper;

    @Override
    public ReviewPointResponse getReviewPoints(String userId) {
        if (!reviewPointRepository.existsByUserId(userId)) throw new IllegalStateException("사용자 정보를 확인해주세요.");
        return ReviewPointResponse.ofReviewPointList(reviewPointRepository.findByUserId(userId), reviewPointWithHistoryDtoMapper);
    }

}
