package com.triple.travelerclubservice.mapper;

import com.triple.travelerclubservice.dto.ReviewPointWithHistoryDto;
import com.triple.travelerclubservice.entity.ReviewPointHistories;
import com.triple.travelerclubservice.entity.ReviewPoints;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewPointWithHistoryDtoMapper extends GenericMapper<ReviewPoints, ReviewPointWithHistoryDto> {

    @Mapping(source = "reviewPoint.id", target = "id")
    @Mapping(source = "reviewPoint.reviewId", target = "reviewId")
    @Mapping(source = "reviewPoint.userId", target = "userId")
    @Mapping(source = "reviewPoint.placeId", target = "placeId")
    @Mapping(source = "reviewPoint.type", target = "type")
    @Mapping(source = "reviewPoint.state", target = "state")
    @Mapping(source = "reviewPoint.amount", target = "amount")
    @Mapping(source = "reviewPoint.createdAt", target = "createdAt")
    @Mapping(source = "reviewPoint.updatedAt", target = "updatedAt")
    @Mapping(source = "reviewPointHistoriesSet", target = "reviewPointHistoryDtoSet")
    ReviewPointWithHistoryDto toDTO(Set<ReviewPointHistories> reviewPointHistoriesSet, ReviewPoints reviewPoint);

}
