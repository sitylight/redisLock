package com.jedis.redisLock.api;

import com.jedis.redisLock.usecase.LockUseCase;
import com.jedis.redisLock.util.RedisLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author lcb 2019-04-27
 */
@RestController
public class LockController {
    @Autowired
    private RedisLockUtil redisLockUtil;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private LockUseCase lockUseCase;


    @GetMapping("/faker")
    public String testLock(final String key) {
        final String requestId = key + "123123";
        boolean isLock = Boolean.FALSE;
        String message = null;
        final Jedis jedis = jedisPool.getResource();
        jedis.set("test", "value");
        try {
//            if (jedisPool.getResource().exists(key)) {
//                message = "获取锁失败";
//            } else {
            if (redisLockUtil.tryGetDistributedLock(jedisPool.getResource(), key, requestId, 60)) {
                isLock = true;
                message = lockUseCase.execute(requestId);
            } else {
                message = "数据已被锁";
            }
//            }
        } catch (final Exception e) {
            System.out.print(e.getCause().getMessage());
            message = e.getMessage();
        } finally {
            if (isLock) {
                final boolean isRelease = redisLockUtil.releaseDistributedLock(jedisPool.getResource(), key, requestId);
                if (isRelease) {
                    message = "released";
                    System.out.println("lock is released");
                } else {
                    message = "expired";
                    System.out.println("lock is expired");
                }
            }
        }
        return message;
    }
}
