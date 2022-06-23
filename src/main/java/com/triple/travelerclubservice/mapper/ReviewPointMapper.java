package com.triple.travelerclubservice.mapper;

import com.triple.travelerclubservice.dto.ReviewPointDto;
import com.triple.travelerclubservice.entity.ReviewPoints;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewPointMapper extends GenericMapper<ReviewPoints, ReviewPointDto> {

}
