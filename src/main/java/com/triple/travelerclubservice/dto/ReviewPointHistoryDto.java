package com.triple.travelerclubservice.dto;

import com.triple.travelerclubservice.enumeration.ReviewPointCause;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewPointHistoryDto {

    protected long id;

    protected ReviewPointCause pointCause;

    protected int amount;

    protected ReviewPointDto reviewPointDto;

    protected LocalDateTime createdAt;

    protected LocalDateTime updatedAt;

}
