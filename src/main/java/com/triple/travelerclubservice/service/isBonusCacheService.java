package com.triple.travelerclubservice.service;

public interface isBonusCacheService {

    boolean fetchBonusCache(String placeId);

    boolean putBonusCache(String placeId, boolean bonusValue);

}
