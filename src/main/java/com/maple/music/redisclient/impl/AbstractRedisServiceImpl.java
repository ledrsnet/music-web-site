package com.maple.music.redisclient.impl;

import com.maple.music.redisclient.RedisService;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author LiangDong
 * @Date 2020/3/17
 */
public abstract class AbstractRedisServiceImpl<K, V> implements RedisService<K, V> {
	@Resource
	private RedisTemplate<K, V> redisTemplate;

	public RedisTemplate<K, V> getRedisTemplate() {
		return redisTemplate;
	}
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
	@Override
	public void set(final K key, final V value, final long expiredTime) {
		BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
		if (expiredTime <= 0) {
			valueOper.set(value);
		} else {
			valueOper.set(value, expiredTime, TimeUnit.MILLISECONDS);
		}
	}
	@Override
	public V get(final K key) {
		BoundValueOperations<K, V> valueOper = redisTemplate.boundValueOps(key);
		return valueOper.get();
	}
	@Override
	public Object getHash(K key, String name){
		Object res = redisTemplate.boundHashOps(key).get(name);
		return res;
	}
	@Override
	public void del(K key) {
		if (redisTemplate.hasKey(key)) {
			redisTemplate.delete(key);
		}
	}

}
