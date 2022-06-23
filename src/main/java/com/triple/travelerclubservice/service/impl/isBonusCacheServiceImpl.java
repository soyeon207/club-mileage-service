package com.triple.travelerclubservice.service.impl;

import com.triple.travelerclubservice.config.RedisCacheConfig;
import com.triple.travelerclubservice.enumeration.ReviewPointState;
import com.triple.travelerclubservice.repository.ReviewPointRepository;
import com.triple.travelerclubservice.service.isBonusCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class isBonusCacheServiceImpl implements isBonusCacheService {

    private final ReviewPointRepository reviewPointRepository;

    @Cacheable(value = RedisCacheConfig.IS_BONUS, key = "'is_bonus_' + #placeId", cacheManager = "redisCacheManager")
    public boolean fetchBonusCache(String placeId) {
        return !reviewPointRepository.existsByPlaceIdAndState(placeId, ReviewPointState.ACTIVE);
    }

    @CachePut(value = RedisCacheConfig.IS_BONUS, key = "'is_bonus_' + #placeId", cacheManager = "redisCacheManager")
    public boolean putBonusCache(String placeId, boolean bonusValue) {
        return bonusValue;
    }

}
