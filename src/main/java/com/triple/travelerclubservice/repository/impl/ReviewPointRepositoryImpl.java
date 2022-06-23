package com.triple.travelerclubservice.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triple.travelerclubservice.entity.ReviewPoints;
import com.triple.travelerclubservice.repository.ReviewPointRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import java.util.List;

import static com.triple.travelerclubservice.entity.QReviewPoints.reviewPoints;

public class ReviewPointRepositoryImpl extends QuerydslRepositorySupport implements ReviewPointRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReviewPointRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ReviewPoints.class);
        this.queryFactory = jpaQueryFactory;
    }

    @Override
    public List<ReviewPoints> findByReviewId(String reviewId) {
        return from(reviewPoints)
                .where(reviewPoints.reviewId.eq(reviewId))
                .fetch();
    }

}
