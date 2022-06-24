package com.triple.travelerclubservice.entity;

import com.triple.travelerclubservice.enumeration.ReviewPointCause;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

}
