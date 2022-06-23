package com.triple.travelerclubservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class ReviewPointWithHistoryDto extends ReviewPointDto {
    protected Set<ReviewPointHistoryDto> reviewPointHistoryDtoSet;
}
