package com.triple.travelerclubservice.enumeration;

import lombok.Getter;

@Getter
public enum ReviewPointCause {
    REVIEW_TEXT ("리뷰 작성"), REVIEW_IMAGE ("리뷰작성-이미지"),
    BONUS ("보너스점수"),
    IMAGE_ADD ("리뷰수정 - 이미지추가"), IMAGE_REMOVE ("리뷰수정 - 이미지삭제"),
    REVIEW_REMOVE("리뷰삭제");

    private final String value;

    ReviewPointCause(String value) {
        this.value = value;
    }

}
