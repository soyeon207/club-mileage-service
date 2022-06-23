package com.triple.travelerclubservice.mapper;

import com.triple.travelerclubservice.dto.ReviewPointHistoryDto;
import com.triple.travelerclubservice.entity.ReviewPointHistories;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewPointHistoryMapper extends GenericMapper<ReviewPointHistories, ReviewPointHistoryDto> {
}
