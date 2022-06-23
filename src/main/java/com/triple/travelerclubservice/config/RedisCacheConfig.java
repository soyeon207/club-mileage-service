package com.triple.travelerclubservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    public static final String IS_BONUS = "IS_BONUS";

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    static final Map<String, Long> CACHE_MAPS;

    static {
        Map<String, Long> map = new HashMap<>();
        map.put(IS_BONUS, 3000L);
        CACHE_MAPS = Collections.unmodifiableMap(map);
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        return new LettuceConnectionFactory(redisStandaloneConfiguration, LettuceClientConfiguration.builder().commandTimeout(Duration.ofMillis(timeout)).build());
    }

    @Bean
    public RedisTemplate<byte[], byte[]> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    @Bean(name = "redisCacheManager")
    public CacheManager cacheManager() {
        RedisCacheConfiguration redisDefaultConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .computePrefixWith(CacheKeyPrefix.simple())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()));

        Map<String, RedisCacheConfiguration> cacheConfigurationMap = new HashMap<>();
        for (Map.Entry<String, Long> stringRedisCacheConfigValuesEntry : CACHE_MAPS.entrySet()) {
            RedisCacheConfiguration redisCacheConfiguration =
                    RedisCacheConfiguration
                            .defaultCacheConfig()
                            .entryTtl(Duration.ofSeconds(stringRedisCacheConfigValuesEntry.getValue()));
            cacheConfigurationMap.put(stringRedisCacheConfigValuesEntry.getKey(), redisCacheConfiguration);
        }

        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory())
                .cacheDefaults(redisDefaultConfiguration)
                .withInitialCacheConfigurations(cacheConfigurationMap)
                .build();
    }
}
