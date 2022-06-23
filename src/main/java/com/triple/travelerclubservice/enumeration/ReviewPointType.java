package com.triple.travelerclubservice.enumeration;

import lombok.Getter;

@Getter
public enum ReviewPointType {
    BONUS(1), TEXT(1), IMAGE(1);

    private final int score;

    ReviewPointType(int score) {
        this.score = score;
    }

}
