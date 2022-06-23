package com.triple.travelerclubservice.controller;

import com.triple.travelerclubservice.dto.ReviewPointResponse;
import com.triple.travelerclubservice.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UsersController {

    private final UsersService usersService;

    @GetMapping("/{userId}/review-points")
    public ReviewPointResponse getReviewPointsByUserId(@PathVariable String userId) {
        return usersService.getReviewPoints(userId);
    }

}
