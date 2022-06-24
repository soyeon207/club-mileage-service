package com.triple.travelerclubservice.controller;

import com.triple.travelerclubservice.dto.PointCreateRequest;
import com.triple.travelerclubservice.dto.ReviewPointDto;
import com.triple.travelerclubservice.service.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventsController {

    private final EventsService eventsService;

    @PostMapping
    public ResponseEntity<List<ReviewPointDto>> createEvent(@Valid @RequestBody PointCreateRequest pointCreateRequest) {
        return ResponseEntity.ok(eventsService.createEvent(pointCreateRequest));
    }

}
