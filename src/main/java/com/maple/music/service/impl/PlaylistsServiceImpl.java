package com.maple.music.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maple.music.dao.PlaylistsDao;
import com.maple.music.dao.UserDao;
import com.maple.music.entity.CategoriesBigConfig;
import com.maple.music.entity.CategoriesConfig;
import com.maple.music.redisclient.RedisService;
import com.maple.music.service.PlaylistsService;
import com.maple.music.util.PinYinUtil;
import com.maple.music.util.RedisTool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/14
 */
@Service("playlistsService")
@Transactional(rollbackFor={Exception.class, RuntimeException.class})
public class PlaylistsServiceImpl implements PlaylistsService {
	private static final Logger log = LoggerFactory.getLogger(PlaylistsServiceImpl.class);

	@Resource
	private PlaylistsDao playlistsDao;
	@Resource
	private UserDao userDao;



	@Override
	public Map<String, Object> getCategoriesInfo() {
		// redis服务及类型准备
		RedisService redisService = RedisTool.getRedisService();
		Type bigCatType = new TypeToken<List<CategoriesBigConfig>>() {}.getType();
		Type smallCatType = new TypeToken<List<CategoriesConfig>>() {}.getType();

		// 查redis缓存
		String bigCat = (String) redisService.get("bigCat");
		String smallCat = (String) redisService.get("smallCat");
		List<CategoriesBigConfig>  bigCategories;
		List<CategoriesConfig> categoriesConfig ;

		//如果redis缓存不存在
		if(StringUtils.isBlank(bigCat)){
			log.info("=====bigCat缓存不存在，查询数据库======");
			bigCategories = playlistsDao.getBigCategories();
			redisService.set("bigCat",new Gson().toJson(bigCategories,bigCatType),1000*60*10);
			log.info("=====将bigCat写入缓存。=====");
		}else{
			log.info("=====缓存命中bigCat=====");
			bigCategories  = new Gson().fromJson(bigCat, bigCatType);
		}
		if(StringUtils.isBlank(smallCat)){
			log.info("=====smallCat缓存不存在，查询数据库======");
			categoriesConfig = playlistsDao.getCategoriesConfig();
			redisService.set("smallCat",new Gson().toJson(categoriesConfig,smallCatType),1000*60*10);
			log.info("=====将smallCat写入缓存成功。=====");
		}else{
			log.info("=====缓存命中smallCat=====");
			categoriesConfig= new Gson().fromJson(smallCat, smallCatType);
		}

		Map<String,Object> map = new HashMap<>();
		map.put("sub",categoriesConfig);
		Map<Integer,String> map1 = new HashMap<>();
		for (CategoriesBigConfig bigCategory : bigCategories) {
			map1.put(bigCategory.getCode(),bigCategory.getCodeText());
		}
		map.put("categories",map1);
		return map;
	}

	@Override
	public List<Map<String,Object>> getPlaylists(String cat, String order) {
		// redis服务及类型准备
		RedisService redisService = RedisTool.getRedisService();
		Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();

		String playlists = (String) redisService.get(PinYinUtil.getPingYin(cat) + order);
		List<Map<String,Object>> list;

		if(StringUtils.isBlank(playlists)){
			log.info("======"+cat+order+"缓存为空========");
			list = playlistsDao.getPlaylists(cat, order);
			redisService.set(PinYinUtil.getPingYin(cat)+order,new Gson().toJson(list,type),1000*60*10);
			log.info("====="+cat+order+"写入缓存成功=====");
		}else{
			log.info("=====缓存"+cat+order+"命中=====");
			list = new Gson().fromJson(playlists,type);
		}
		return list;
	}

	@Override
	public List<Map<String,Object>> getPlaylists() {
		// redis服务及类型准备
		RedisService redisService = RedisTool.getRedisService();
		Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();

		String playlists = (String) redisService.get("allPlaylists");
		List<Map<String,Object>> list;

		if(StringUtils.isBlank(playlists)){
			log.info("======allPlaylists缓存为空========");
			list = playlistsDao.getPlaylists();
			redisService.set("allPlaylists",new Gson().toJson(list,type),1000*60*10);
			log.info("=====allPlaylists写入缓存成功=====");
		}else{
			log.info("=====缓存allPlaylists命中=====");
			list = new Gson().fromJson(playlists,type);
		}
		return list;
	}
}
