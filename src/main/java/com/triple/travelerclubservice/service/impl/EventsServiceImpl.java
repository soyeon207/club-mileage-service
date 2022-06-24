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
            case ADD:
                // 리뷰 생성 이벤트
                boolean isExistsReview = reviewPointRepository.existsByUserIdAndPlaceId(pointCreateRequest.getUserId(), pointCreateRequest.getPlaceId());
                if (isExistsReview) throw new IllegalStateException("이미 해당 장소에 대해서 리뷰를 작성하셨습니다.");

                if (isBonusCacheService.fetchBonusCache(pointCreateRequest.getPlaceId())) {
                    isBonusCacheService.putBonusCache(pointCreateRequest.getPlaceId(), false);
                    reviewPointsList.add(ReviewPoints.ofCreate(pointCreateRequest, ReviewPointType.BONUS, ReviewPointCause.BONUS));
                }

                for (Map.Entry<ReviewPointType, ReviewPointCause> reviewPointCauseEntry : pointCreateRequest.getReviewPointCauseMap().entrySet()) {
                    reviewPointsList.add(ReviewPoints.ofCreate(pointCreateRequest, reviewPointCauseEntry.getKey(), reviewPointCauseEntry.getValue()));
                }

                reviewPointRepository.saveAll(reviewPointsList);
                break;
            case MOD:
                // 리뷰 수정 이벤트
                reviewPointsList = reviewPointRepository.findByReviewId(pointCreateRequest.getReviewId());
                if (CollectionUtils.isEmpty(reviewPointsList)) throw new IllegalStateException("작성하신 리뷰가 없습니다");
                List<ReviewPoints> imageReviewPointList = reviewPointsList
                        .stream()
                        .filter(reviewPoint -> reviewPoint.getType().equals(ReviewPointType.IMAGE) && reviewPoint.getState().equals(ReviewPointState.ACTIVE))
                        .collect(Collectors.toList());

                if (pointCreateRequest.checkImageScore() && CollectionUtils.isEmpty(imageReviewPointList)) {
                    reviewPointRepository.save(ReviewPoints.ofCreate(pointCreateRequest, ReviewPointType.IMAGE, ReviewPointCause.IMAGE_ADD));
                } else if (!pointCreateRequest.checkImageScore() && !CollectionUtils.isEmpty(imageReviewPointList)) {
                    for (ReviewPoints reviewPoints : imageReviewPointList) {
                        reviewPoints.changeState(ReviewPointState.WITHDRAW, ReviewPointCause.IMAGE_REMOVE);
                    }
                }
                break;
            case DELETE:
                // 리뷰 삭제 이벤트
                reviewPointsList = reviewPointRepository.findByReviewId(pointCreateRequest.getReviewId());
                if (CollectionUtils.isEmpty(reviewPointsList)) throw new IllegalStateException("작성하신 리뷰가 없습니다");
                for (ReviewPoints reviewPoints : reviewPointsList) {
                    reviewPoints.changeState(ReviewPointState.WITHDRAW, ReviewPointCause.REVIEW_REMOVE);
                    if (reviewPoints.getType().equals(ReviewPointType.BONUS)) {
                        isBonusCacheService.putBonusCache(pointCreateRequest.getPlaceId(), true);
                    }
                }
                break;
            default:
                throw new IllegalStateException("존재하지 않는 타입입니다.");
        }

        return reviewPointsList
                .stream()
                .map(reviewPointMapper::toDto)
                .collect(Collectors.toList());
    }

}
