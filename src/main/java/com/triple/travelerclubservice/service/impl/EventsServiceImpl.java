package com.triple.travelerclubservice.service.impl;

import com.triple.travelerclubservice.dto.PointCreateRequest;
import com.triple.travelerclubservice.dto.ReviewPointDto;
import com.triple.travelerclubservice.entity.ReviewPoints;
import com.triple.travelerclubservice.enumeration.ReviewPointCause;
import com.triple.travelerclubservice.enumeration.ReviewPointState;
import com.triple.travelerclubservice.enumeration.ReviewPointType;
import com.triple.travelerclubservice.mapper.ReviewPointMapper;
import com.triple.travelerclubservice.repository.ReviewPointRepository;
import com.triple.travelerclubservice.service.isBonusCacheService;
import com.triple.travelerclubservice.service.EventsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private final ReviewPointMapper reviewPointMapper;
    private final ReviewPointRepository reviewPointRepository;
    private final isBonusCacheService isBonusCacheService;

    @Override
    public List<ReviewPointDto> createEvent(PointCreateRequest pointCreateRequest) {
        List<ReviewPoints> reviewPointsList = new ArrayList<>();
        switch (pointCreateRequest.getAction()) {
            case ADD: // 리뷰 생성 이벤트
                boolean isExistsReview = reviewPointRepository.existsByUserIdAndPlaceId(pointCreateRequest.getUserId(), pointCreateRequest.getPlaceId());
                if (isExistsReview) throw new IllegalStateException("이미 해당 장소에 대해서 리뷰를 작성하셨습니다.");

                // 보너스 포인트
                if (isBonusCacheService.fetchBonusCache(pointCreateRequest.getPlaceId())) {
                    isBonusCacheService.putBonusCache(pointCreateRequest.getPlaceId(), false);
                    reviewPointsList.add(ReviewPoints.ofCreate(pointCreateRequest, ReviewPointType.BONUS, ReviewPointCause.BONUS));
                }

                // 텍스트 및 이미지 포인트
                for (Map.Entry<ReviewPointType, ReviewPointCause> reviewPointCauseEntry : pointCreateRequest.getReviewPointCauseMap().entrySet()) {
                    reviewPointsList.add(ReviewPoints.ofCreate(pointCreateRequest, reviewPointCauseEntry.getKey(), reviewPointCauseEntry.getValue()));
                }

                reviewPointRepository.saveAll(reviewPointsList);
                break;
            case MOD: // 리뷰 수정 이벤트
                reviewPointsList = getReviewPointsList(pointCreateRequest.getReviewId(), pointCreateRequest.getUserId());
                ReviewPoints imageReviewPoint = reviewPointsList
                        .stream()
                        .filter(reviewPoints -> reviewPoints.getType().equals(ReviewPointType.IMAGE))
                        .findFirst().orElse(null);

                if (pointCreateRequest.isAddImage(imageReviewPoint)) { // 이미지를 추가한 경우
                    if (imageReviewPoint == null) {
                        // 이전에 이미지 포인트를 받은 적이 없으면 -> review_points & review_point_histories 데이터 생성
                        ReviewPoints reviewPoints = ReviewPoints.ofCreate(pointCreateRequest, ReviewPointType.IMAGE, ReviewPointCause.IMAGE_ADD);
                        reviewPointsList.add(reviewPoints);
                        reviewPointRepository.save(reviewPoints);
                    } else {
                        // 이전에 이미지 포인트를 받은 적이 있는데 회수 당한 경우 -> ACTIVE 로 수정 & review_point_historeis 데이터 생성
                        imageReviewPoint.changeState(ReviewPointState.ACTIVE, ReviewPointCause.IMAGE_ADD);
                    }
                } else if (pointCreateRequest.isRemoveImage(imageReviewPoint)) { // 이미지를 삭제한 경우
                    imageReviewPoint.changeState(ReviewPointState.WITHDRAW, ReviewPointCause.IMAGE_REMOVE);
                }

                break;
            case DELETE: // 리뷰 삭제 이벤트
                reviewPointsList = getReviewPointsList(pointCreateRequest.getReviewId(), pointCreateRequest.getUserId());
                for (ReviewPoints reviewPoints : reviewPointsList) {
                    if (reviewPoints.getState().equals(ReviewPointState.ACTIVE)) {
                        reviewPoints.changeState(ReviewPointState.WITHDRAW, ReviewPointCause.REVIEW_REMOVE);
                        if (reviewPoints.getType().equals(ReviewPointType.BONUS)) isBonusCacheService.putBonusCache(pointCreateRequest.getPlaceId(), true);
                    }
                }
                break;
            default: throw new IllegalStateException("존재하지 않는 타입입니다.");
        }

        return reviewPointsList
                .stream()
                .map(reviewPointMapper::toDto)
                .collect(Collectors.toList());
    }

    private List<ReviewPoints> getReviewPointsList(String reviewId, String userId) {
        List<ReviewPoints> reviewPointsList = reviewPointRepository.findByReviewIdAndUserId(reviewId, userId);
        if (CollectionUtils.isEmpty(reviewPointsList)) throw new IllegalStateException("작성하신 리뷰가 없습니다");
        if (reviewPointsList.stream().noneMatch(a->a.getState().equals(ReviewPointState.ACTIVE))) throw new IllegalStateException("삭제된 리뷰입니다.");
        return reviewPointsList;
    }

}
