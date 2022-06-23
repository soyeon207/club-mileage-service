package com.triple.travelerclubservice.service;

import com.triple.travelerclubservice.dto.PointCreateRequest;
import com.triple.travelerclubservice.dto.ReviewPointDto;

import java.util.List;

public interface EventsService {

    List<ReviewPointDto> createEvent(PointCreateRequest pointCreateRequest);

}
