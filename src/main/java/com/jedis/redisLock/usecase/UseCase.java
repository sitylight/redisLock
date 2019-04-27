package com.jedis.redisLock.usecase;

/**
 * @author lcb 2019-04-27
 */
@FunctionalInterface
public interface UseCase<V, T> {
    T execute(V v);
}
