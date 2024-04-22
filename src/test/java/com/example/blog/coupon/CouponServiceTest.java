package com.example.blog.coupon;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class CouponServiceTest {
    @Autowired
    CouponService couponService;
    @Autowired
    CouponRepository couponRepository;

    @Test
    void couponDecreaseSuccess() throws Exception {
        Coupon coupon = new Coupon("COUPON", 100L);
        couponRepository.save(coupon);

        int couponCount = 100;
        CountDownLatch latch = new CountDownLatch(couponCount);
        ExecutorService executorService = Executors.newFixedThreadPool(couponCount);
        for (int i = 0; i < couponCount; i++) {
            executorService.submit(() -> {
               try {
                   couponService.couponDecrease(coupon.getId());
               } finally {
                   latch.countDown();
               }
            });
        }

        latch.await();
        executorService.shutdown();

        Coupon coupon1 = couponRepository.findById(coupon.getId())
                .orElseThrow();
        Assertions.assertThat(coupon1.getAvailableStock()).isZero();
    }
}