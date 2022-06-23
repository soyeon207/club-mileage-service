package com.triple.travelerclubservice.dto;

import com.triple.travelerclubservice.enumeration.ReviewPointState;
import com.triple.travelerclubservice.enumeration.ReviewPointType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewPointDto {
    protected long id;

    protected String reviewId;

    protected String userId;

    protected String placeId;

    protected ReviewPointType type;

    protected ReviewPointState state;

    protected int amount;

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;
}
