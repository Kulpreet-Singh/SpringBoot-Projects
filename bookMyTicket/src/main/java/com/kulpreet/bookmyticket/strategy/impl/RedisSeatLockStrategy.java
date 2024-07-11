package com.kulpreet.bookmyticket.strategy.impl;

import com.kulpreet.bookmyticket.strategy.SeatLockStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisSeatLockStrategy implements SeatLockStrategy {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final long LOCK_EXPIRE_TIME = 600; // 10 minutes

    @Override
    public boolean lockSeat(Long showId, Long seatId, String userId) {
//        log.info("Locking seat: {} for show: {} by user: {}", seatId, showId, userId);
        String key = "seat_lock_" + showId + "_" + seatId;
        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))){
            String lockOwner = (String) redisTemplate.opsForValue().get(key);
            return userId.equals(lockOwner);
        }
        Boolean isLocked = redisTemplate.opsForValue().setIfAbsent(key, userId, LOCK_EXPIRE_TIME, TimeUnit.SECONDS);
        return isLocked != null && isLocked;
    }

    @Override
    public boolean unlockSeat(Long showId, Long seatId, String userId) {
//        log.info("Unlocking seat: {} for show: {} by user: {}", seatId, showId, userId);
        String key = "seat_lock_" + showId + "_" + seatId;
        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))){
            String lockOwner = (String) redisTemplate.opsForValue().get(key);
            if (userId.equals(lockOwner)) {
                redisTemplate.delete(key);
                return true;
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean isSeatLocked(Long showId, Long seatId, String userId) {
        String key = "seat_lock_" + showId + "_" + seatId;
        if(Boolean.TRUE.equals(redisTemplate.hasKey(key))){
            String lockOwner = (String) redisTemplate.opsForValue().get(key);
            return !userId.equals(lockOwner);
        }
        return false;
    }
}
