package com.triple.travelerclubservice.dto;

import com.triple.travelerclubservice.entity.ReviewPoints;
import com.triple.travelerclubservice.mapper.ReviewPointWithHistoryDtoMapper;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ReviewPointResponse {

    protected int activeTotalPoint = 0;

    protected List<ReviewPointWithHistoryDto> reviewPointHistoryDtoList = new ArrayList<>();

    public static ReviewPointResponse ofReviewPointList(List<ReviewPoints> reviewPointsList, ReviewPointWithHistoryDtoMapper reviewPointWithHistoryDtoMapper) {
        ReviewPointResponse instance = new ReviewPointResponse();
        for (ReviewPoints reviewPoints : reviewPointsList) {
            instance.reviewPointHistoryDtoList.add(reviewPointWithHistoryDtoMapper.toDTO(reviewPoints.getReviewPointHistoriesSet(), reviewPoints));
            instance.activeTotalPoint += reviewPoints.getAmount();
        }
        return instance;
    }

}
