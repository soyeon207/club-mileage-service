package com.triple.travelerclubservice.entity;

import com.triple.travelerclubservice.enumeration.ReviewPointCause;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ReviewPointHistories extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "point_cause")
    @Enumerated(value = EnumType.STRING)
    private ReviewPointCause pointCause;

    @Column(name = "amount")
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY)
    private ReviewPoints reviewPoint;

    public static ReviewPointHistories ofCreate(ReviewPoints reviewPoints, ReviewPointCause reviewPointCause, int amount) {
        ReviewPointHistories instance = new ReviewPointHistories();
        instance.pointCause = reviewPointCause;
        instance.amount = amount;
        instance.reviewPoint = reviewPoints;
        return instance;
    }

}
