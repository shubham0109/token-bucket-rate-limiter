package com.ratelimiter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TokenBucketRateLimiter implements RateLimiter{

    private int refreshLimit; //no. of new tokens every second
    private int bucketCapacity;
    private AtomicInteger currentCapacity;
    private AtomicLong lastUpdateTime;

    public TokenBucketRateLimiter(int refreshLimit, int bucketCapacity){
        this.refreshLimit = refreshLimit;
        this.bucketCapacity = bucketCapacity;
        this.currentCapacity = new AtomicInteger(bucketCapacity);
        this.lastUpdateTime = new AtomicLong(System.currentTimeMillis());

    }

    @Override
    public boolean grantAccess(){

        refreshTokens();
        if (currentCapacity.get() > 0){
            currentCapacity.decrementAndGet();
            return true;
        }

        return false;
    }

    public void refreshTokens(){
        long currentTime = System.currentTimeMillis();

        int new_tokens = (int) (((currentTime - lastUpdateTime.get())/1000)*refreshLimit);

        int curr_cap = Math.min(currentCapacity.get() + new_tokens, bucketCapacity);
        currentCapacity.getAndSet(curr_cap);

        lastUpdateTime.set(System.currentTimeMillis());
    }

}
