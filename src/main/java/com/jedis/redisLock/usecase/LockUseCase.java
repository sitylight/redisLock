package com.jedis.redisLock.usecase;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author lcb 2019-04-27
 */
@Component
public class LockUseCase implements UseCase<String, String>{

    @Override
    public String execute(final String s) {
        try {
            Thread.sleep(10000);
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("execute time: " + LocalDateTime.now());
        return "success";
    }
}
