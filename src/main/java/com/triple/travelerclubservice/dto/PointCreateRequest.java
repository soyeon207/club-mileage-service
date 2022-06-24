package com.triple.travelerclubservice.dto;

import com.querydsl.core.util.StringUtils;
import com.triple.travelerclubservice.entity.ReviewPoints;
import com.triple.travelerclubservice.enumeration.PointCreateAction;
import com.triple.travelerclubservice.enumeration.ReviewPointCause;
import com.triple.travelerclubservice.enumeration.ReviewPointState;
import com.triple.travelerclubservice.enumeration.ReviewPointType;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class PointCreateRequest {

    @NotEmpty
    protected String type;                      // 이벤트 타입

    @NotNull
    protected PointCreateAction action;         // 이벤트 액션

    @NotEmpty
    protected String reviewId;                  // UUID 포맷의 review 아이디

    @NotEmpty
    protected String content;                   // 리뷰 내용

    @NotNull
    protected List<String> attachedPhotoIds;    // 리뷰에 첨부된 이미지 id 리스트

    @NotEmpty
    protected String userId;                    // 리뷰 작성자 아이디

    @NotEmpty
    protected String placeId;                   // 리뷰 작성 장소 아이디

    public boolean checkTextScore() {
        // 내용 점수 중 `1점 이상 텍스트 작성 점수` 를 받을 수 있는지 확인 하는 메소드
        return !StringUtils.isNullOrEmpty(this.getContent());
    }

    public boolean checkImageScore() {
        // 내용 점수 중 `1점 이상 사진 첨부` 를 받을 수 있는지 확인 하는 메소드
        return !CollectionUtils.isEmpty(this.getAttachedPhotoIds());
    }

    public Map<ReviewPointType, ReviewPointCause> getReviewPointCauseMap() {
        Map<ReviewPointType, ReviewPointCause> reviewPointCauseMap = new HashMap<>();
        if (checkTextScore()) reviewPointCauseMap.put(ReviewPointType.TEXT, ReviewPointCause.REVIEW_TEXT);
        if (checkImageScore()) reviewPointCauseMap.put(ReviewPointType.IMAGE, ReviewPointCause.REVIEW_IMAGE);
        return reviewPointCauseMap;
    }

    public boolean isAddImage(ReviewPoints reviewPoints) {
        // 수정 시 이미지가 추가되었는지 확인하기 위한 메소드
        // 1. 이미지 아이디가 파라미터로 넘어왔고,
        // 2. 이미지 포인트를 받은 적이 없거나, 이미지 포인트가 삭제된 경우
        return checkImageScore()
                && (reviewPoints == null || reviewPoints.getState().equals(ReviewPointState.WITHDRAW));
    }

    public boolean isRemoveImage(ReviewPoints reviewPoints) {
        // 수정 시 이미지가 삭제되었는지 확인하기 위한 메소드
        // 1. 이미지 아이디가 파라미터로 넘어오지 않았고,
        // 2. 현재 이미지 포인트가 active 한 경우
        return !checkImageScore()
                && reviewPoints != null
                && reviewPoints.getState().equals(ReviewPointState.ACTIVE);
    }

}
