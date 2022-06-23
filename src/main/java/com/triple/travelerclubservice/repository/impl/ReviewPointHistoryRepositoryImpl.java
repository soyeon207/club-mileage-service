package com.triple.travelerclubservice.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReviewPointHistoryRepositoryImpl {

    private final JPAQueryFactory queryFactory;



}
