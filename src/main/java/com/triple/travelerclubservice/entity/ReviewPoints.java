package com.triple.travelerclubservice.entity;

import com.triple.travelerclubservice.dto.PointCreateRequest;
import com.triple.travelerclubservice.enumeration.ReviewPointCause;
import com.triple.travelerclubservice.enumeration.ReviewPointState;
import com.triple.travelerclubservice.enumeration.ReviewPointType;
import lombok.Getter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class ReviewPoints extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "review_id")
    private String reviewId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "place_id")
    private String placeId;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private ReviewPointType type;

    @Column(name = "state")
    @Enumerated(value = EnumType.STRING)
    private ReviewPointState state;

    @Column(name = "amount")
    private int amount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "reviewPoint")
    private Set<ReviewPointHistories> reviewPointHistoriesSet = new HashSet<>();

    public static ReviewPoints ofCreate(PointCreateRequest pointCreateRequest, ReviewPointType reviewPointType, ReviewPointCause reviewPointCause) {
        ReviewPoints instance = new ReviewPoints();
        instance.reviewId = pointCreateRequest.getReviewId();
        instance.userId = pointCreateRequest.getUserId();
        instance.placeId = pointCreateRequest.getPlaceId();
        instance.type = reviewPointType;
        instance.state = ReviewPointState.ACTIVE;
        instance.amount = reviewPointType.getScore();
        instance.reviewPointHistoriesSet.add(ReviewPointHistories.ofCreate(instance, reviewPointCause, instance.getAmount()));
        return instance;
    }

    public void changeState(ReviewPointState reviewPointState, ReviewPointCause reviewPointCause) {
        this.state = reviewPointState;
        reviewPointHistoriesSet.add(ReviewPointHistories.ofCreate(this, reviewPointCause, this.getAmount() * -1));
    }

    public void changeWithDraw(ReviewPointCause reviewPointCause) {
        this.state = ReviewPointState.WITHDRAW;
        reviewPointHistoriesSet.add(ReviewPointHistories.ofCreate(this, reviewPointCause, this.getAmount() * -1));
    }


}
