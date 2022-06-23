package com.triple.travelerclubservice.dto;

import com.querydsl.core.util.StringUtils;
import com.triple.travelerclubservice.enumeration.PointCreateAction;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class PointCreateRequest {

    @NotNull
    protected String type;                      // 이벤트 타입

    @NotNull
    protected PointCreateAction action;         // 이벤트 액션

    @NotNull
    protected String reviewId;                  // UUID 포맷의 review 아이디

    @NotNull
    protected String content;                   // 리뷰 내용

    @NotNull
    protected List<String> attachedPhotoIds;    // 리뷰에 첨부된 이미지 id 리스트

    @NotNull
    protected String userId;                    // 리뷰 작성자 아이디

    @NotNull
    protected String placeId;                   // 리뷰 작성 장소 아이디

    public boolean checkTextScore() {
        // 내용 점수 중 `1점 이상 텍스트 작성 점수` 를 받을 수 있는지 확인 하는 메소드
        return !StringUtils.isNullOrEmpty(this.getContent());
    }

    public boolean checkImageScore() {
        // 내용 점수 중 `1점 이상 사진 첨부` 를 받을 수 있는지 확인 하는 메소드
        return !CollectionUtils.isEmpty(this.getAttachedPhotoIds());
    }

}
