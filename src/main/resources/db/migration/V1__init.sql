create table review_points
(
    id        int auto_increment comment '기본키' primary key,
    review_id varchar(100)         not null comment '포인트 부여된 리뷰 아이디 (테이블이 없어서 FK x)',
    user_id   varchar(100)         not null comment '포인트 지급받은 유저 아이디 (테이블이 없어서 FK x)',
    place_id  varchar(100)         not null comment '포인트를 지급받은 장소 아이디 (테이블이 없어서 FK x)',
    type      varchar(100) not null comment '포인트 유형 (BONUS / TEXT / IMAGE)',
    state     varchar(100) not null comment '포인트 상태 (ACTIVE / WITHDRAW)',
    amount    int         not null comment '포인트 값',
    created_at datetime     not null,
    updated_at datetime     not null
);

create table review_point_histories
(
    id              int auto_increment comment '기본키',
    review_point_id int         not null comment 'review_points FK',
    point_cause      varchar(20) not null comment '포인트 발생 원인',
    amount          int         not null comment '포인트 값',
    created_at datetime     not null,
    updated_at datetime     not null,
    constraint review_point_histories_pk primary key (id),
    constraint review_point_histories_review_points_id_fk foreign key (review_point_id) references review_points (id)
);

