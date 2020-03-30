package com.maple.music.redisclient;

import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/17
 */
public interface RedisService<K,V> {
	public void set(K key, V value, long expiredTime);
	public V get(K key);
	public Object getHash(K key, String name);
	public void del(K key);
	public void setMap(String mapName, Map<String,Object> modelMap);
	public Map<Object,Object> getMapValue(String mapName);
}
