package com.example.blog.coupon;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    private final RedissonClient redissonClient;

    @Transactional
    public void couponDecrease(Long couponId) {
        String lockKey = "COUPON_DECREASE_" + couponId;
        RLock rLock = redissonClient.getLock(lockKey);

        try {
            boolean available = rLock.tryLock(5L, 3L, TimeUnit.SECONDS);
            if (!available) {
                log.info("redisson getLock fail.");
                throw new RuntimeException("redisson getLock fail.");
            }

            Coupon coupon = couponRepository.findById(couponId)
                    .orElseThrow();
            coupon.decrease();
        } catch (InterruptedException e) {
            throw new RuntimeException();
        } finally {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    if (rLock.isHeldByCurrentThread()) {
                        rLock.unlock();
                    }
                }
            });
        }
    }

    @Transactional
    public void couponDecreaseWithLock(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow();
        coupon.decrease();
    }
}