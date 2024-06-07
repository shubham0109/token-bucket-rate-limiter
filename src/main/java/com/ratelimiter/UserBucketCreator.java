package com.ratelimiter;

import java.util.HashMap;
import java.util.Map;

public class UserBucketCreator {

    Map<Integer, TokenBucketRateLimiter> mp;

    public UserBucketCreator(int userId){
        mp = new HashMap<>();
        mp.put(userId, new TokenBucketRateLimiter(1, 10));
    }

    public void accessApplication(int userId){

        TokenBucketRateLimiter tb = mp.get(userId);

        if (tb.grantAccess() == true){
            System.err.println("Access Application!!");
        }else{
            System.out.println("TOO MANY REQUESTS!!!!!");
        }

    }

}
