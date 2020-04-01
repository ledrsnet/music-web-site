package com.maple.music.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maple.music.dao.*;
import com.maple.music.entity.*;
import com.maple.music.redisclient.RedisService;
import com.maple.music.service.PlaylistsService;
import com.maple.music.util.PinYinUtil;
import com.maple.music.util.RedisTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
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
	@Resource
	private CategorieDao categorieDao;
	@Resource
	private AlbumDao albumDao;
	@Resource
	private SingerDao singerDao;


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

	@Override
	public Map<String, Object> getPlaylistDetail(BigInteger id) {
		Map<String,Object> map = new HashMap<>();
		// 获取歌单信息
		Playlists playlist= playlistsDao.getPlaylistInfo(id);
		map.put("playlist",playlist);
		// 获取创建人信息
		map.put("nickname",userDao.getNicknameByUserId((playlist.getUserId()).longValue()));
		// 获取分类信息数组
		String tags = playlist.getTags();
		if(StringUtils.isNotBlank(tags)){
			String[] tagsArray = tags.split(",");
			String[] names = new String[3];
			for (int i = 0; i < tagsArray.length; i++) {
				names[i]=categorieDao.getNameById((Integer.parseInt(tagsArray[i])));
			}
			map.put("tags",names);
		}

		// 获取歌单关联的歌曲
		List<BigInteger> list = playlistsDao.getSongIds(id);
		map.put("trackIds",list);

		return map;
	}

	@Override
	public Map<String, Object> getSongsByIds(String ids) {
		// redis服务及类型准备
		RedisService redisService = RedisTool.getRedisService();
		Map<String,Object> map = new HashMap<>();
		map = redisService.getMapValue(ids);
		if(!map.isEmpty()){
			log.info("========map缓存命中========");
			return map;
		}else{
			List<Map<String, Object>> songs = playlistsDao.getSongsByIds(ids);
			List<SongsDto> songsDtos = new ArrayList<>();
			if(songs!=null&&songs.size()>0){
				for (int i = 0; i < songs.size(); i++) {
					SongsDto songsDto = new SongsDto();
					songsDto.setAlbum(new Album((BigInteger) songs.get(i).get("albumId"),(String)songs.get(i).get("albumName")));
					songsDto.setId((BigInteger) songs.get(i).get("songId"));
					songsDto.setName((String) songs.get(i).get("songName"));
					songsDto.setDateTime((Integer) songs.get(i).get("dt"));
					String alia = (String) songs.get(i).get("alia");
					String[] split = new String[0];
					if(StringUtils.isNotBlank(alia)){
						split = alia.split(",");
					}
					songsDto.setAlia(split);
					String singerId = (String) songs.get(i).get("singerId");
					String singerName = (String) songs.get(i).get("singerName");
					String[] singerIds = singerId.split(",");
					String[] singerNames = singerName.split(",");
					List<Singer> singerList = new ArrayList<>();
					for (int i1 = 0; i1 < singerIds.length; i1++) {
						Singer singer = new Singer();
						singer.setId(new BigInteger(singerIds[i1]));
						if(i1<singerNames.length){
							singer.setName(singerNames[i1]);
						}else{
							singer.setName("");
						}
						singerList.add(singer);
					}
					songsDto.setSingers(singerList);
					songsDtos.add(songsDto);
				}
				map.put("songs",songsDtos);
				map.put("code",200);
			}else{
				map.put("code",500);
			}
			log.info("========map缓存未命中========");
			redisService.setMap(ids,map);
			log.info("========map写入缓存========");
		}

		return map;
	}

	@Override
	public Map<String, Object> getReCommendPlaylist() {
		// redis服务及类型准备
		RedisService redisService = RedisTool.getRedisService();
		Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();

		String playlists = (String) redisService.get("reCommendPlaylist");
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<>();

		if(StringUtils.isBlank(playlists)){
			log.info("======reCommendPlaylist缓存为空========");
			list = playlistsDao.getReCommendPlaylist();
			redisService.set("reCommendPlaylist",new Gson().toJson(list,type),1000*60*10);
			log.info("=====reCommendPlaylist写入缓存成功=====");
		}else{
			log.info("=====缓存reCommendPlaylist命中=====");
			list = new Gson().fromJson(playlists,type);
		}
		map.put("playlists",list);
		return map;
	}

	@Override
	public Map<String, Object> getCommentForPlaylist(String id) {
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list = playlistsDao.getCommentForPlaylist(id);
		JSONArray jsonArray = new JSONArray();
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				JSONObject jo = new JSONObject();
				JSONObject user= new JSONObject();
				user.put("userId",list.get(i).get("user_id"));
				user.put("nickname",list.get(i).get("nickname"));
				user.put("avatarUrl",list.get(i).get("avatar_url"));
				jo.put("user",user);
				jo.put("commentId",list.get(i).get("comments_id"));
				jo.put("content",list.get(i).get("content"));
				jo.put("time",list.get(i).get("create_time"));
				jsonArray.add(jo);
			}
			map.put("hotComments",jsonArray);
		}
		return map;
	}

	@Override
	public int addComment(String txt, BigInteger playlistId, Long userId) {
		int i =playlistsDao.addComment(txt,playlistId,userId);
		return i;
	}

	@Override
	public int addFavorite(UserFavorite userFavorite) {
		return playlistsDao.addFavorite(userFavorite);
	}

}
