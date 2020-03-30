package com.maple.music.web.action;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.maple.music.entity.*;
import com.maple.music.service.AlbumService;
import com.maple.music.service.CrawlerService;
import com.maple.music.service.SingerService;
import com.maple.music.service.UserService;
import com.maple.music.util.ConversionUtils;
import com.maple.music.util.FileUtils;
import com.maple.music.util.HttpClientUtils;
import com.maple.music.util.MD5;
import com.opensymphony.xwork2.ActionSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.*;
import java.math.BigInteger;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/2
 */
@Controller("crawlerAction")
@Scope("prototype")
public class CrawlerAction extends ActionSupport {
	public static final Logger log = LoggerFactory.getLogger(CrawlerAction.class);
	public static final String FASTDFS_PATH = "http://121.36.244.33:8080/";

	@Resource
	private CrawlerService crawlerService;

	@Resource
	private UserService userService;

	@Resource
	private SingerService singerService;

	@Resource
	private FastFileStorageClient fastFileStorageClient;

	@Resource
	private AlbumService albumService;


	/**
	 * 1.爬取歌单分类数据写入数据库
	 *
	 * @return
	 */
	public String insertCateGories() {
		try {
			String resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:3000/playlist/catlist");
			JSONObject dataJson = JSONObject.fromObject(resJson);
			JSONArray sub = dataJson.getJSONArray("sub");
			PrintWriter writer = null;
			//解决response输出乱码
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			writer = ServletActionContext.getResponse().getWriter();
			if (sub == null) {
				writer.println("爬取分类数据为空！");
			} else {
				try {
					for (int i = 0; i < sub.size(); i++) {
						JSONObject o = (JSONObject) sub.get(i);
						CategoriesConfig cat = new CategoriesConfig();
						cat.setName(o.getString("name"));
						cat.setCategory(o.getInt("category"));
						boolean flag = o.getBoolean("hot");
						if (flag == true) {
							cat.setHot(1);
						} else {
							cat.setHot(0);
						}
						crawlerService.insertCategory(cat);
					}
					writer.println("爬取分类成功，写入数据库成功！");
				} catch (Exception e) {
					writer.println("爬取分类成功，写入数据库失败！");
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 2.爬取歌单数据插入数据库
	 *
	 * @return
	 */
	public String insertPlaylists() {
		try {
			String resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:3000/top/playlist?limit=1301");
			JSONObject dataJson = JSONObject.fromObject(resJson);
			JSONArray playlists = dataJson.getJSONArray("playlists");
			PrintWriter writer = null;
			//解决response输出乱码
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			writer = ServletActionContext.getResponse().getWriter();
			if (playlists == null) {
				writer.println("爬取分类数据为空！");
			} else {
				JSONObject o = null;
				JSONObject creator = null;
				String coverImgFastdfsPath = null;
				String avatarImgFastdfsPath = null;
				try {
					for (int i = 0; i < playlists.size(); i++) {
						o = (JSONObject) playlists.get(i);
						if (isPlaylistExists(BigInteger.valueOf(o.getLong("id")))) {
							log.info("歌单已存在，继续爬取下一个.");
							continue;
						}
						Playlists lists = new Playlists();
						lists.setId(BigInteger.valueOf(o.getLong("id")));
						lists.setName(o.getString("name"));
						lists.setUserId(BigInteger.valueOf(o.getLong("userId")));
						lists.setCreateTime(ConversionUtils.stampToDate(o.getLong("createTime")));
						lists.setUpdateTime(ConversionUtils.stampToDate(o.getLong("updateTime")));
						lists.setSubscribedCount(BigInteger.valueOf(o.getLong("subscribedCount")));
						lists.setTrackCount(o.getInt("trackCount"));

						// =====下载图片上传到fastdfs=====
						String coverImgUrl = o.getString("coverImgUrl");
						coverImgFastdfsPath = download2UploadFile(coverImgUrl, "jpg");
						// 存储fastdfs的地址
						lists.setCoverImgUrl(coverImgFastdfsPath);
						lists.setDescription(o.getString("description"));
						// =====fastdfs存储完成=====

						// 设置歌单的标签
						JSONArray tags = o.getJSONArray("tags");
						StringBuilder tagsStr = new StringBuilder();
						for (int j = 0; j < tags.size(); j++) {
							Integer categoryId = crawlerService.getCategoryId((String) tags.get(j));
							if (categoryId == null) {
								continue;
							}
							if (j == tags.size() - 1) {
								tagsStr.append(categoryId);
							} else {
								tagsStr.append(categoryId);
								tagsStr.append(",");
							}
						}
						lists.setTags(tagsStr.toString());
						// 设置歌单标签完成

						lists.setPlayCount(BigInteger.valueOf(o.getLong("playCount")));

						// 新增用户
						creator = o.getJSONObject("creator");
						if (isUserExists(creator.getLong("userId"))) {
							log.info("用户已存在，继续爬取下一个.");
							continue;
						}
						User user = new User();
						user.setUserId(creator.getLong("userId"));
						user.setUsername("test" + i);
						user.setPassword(MD5.encryptPassword("redhat", "123"));
						user.setNickname(creator.getString("nickname"));
						user.setCreatTime(ConversionUtils.stampToDate(1521047305000L));
						user.setLastTime(ConversionUtils.stampToDate(1521047305000L));
						// =====下载图片上传到fastdfs=====
						String avatarImgUrl = creator.getString("avatarUrl");
						avatarImgFastdfsPath = download2UploadFile(avatarImgUrl, "jpg");
						// 存储fastdfs的地址
						user.setAvatarUrl(avatarImgFastdfsPath);
						// =====fastdfs存储完成=====
						user.setState(1);
						user.setSignature(creator.getString("signature"));
						user.setGender(creator.getInt("gender"));
						userService.saveUser(user);
						crawlerService.insertPlaylists(lists);
					}
					writer.println("爬取歌单成功，写入数据库成功！");
				} catch (Exception e) {
					writer.println("爬取歌单成功，写入数据库失败！");
					if (StringUtils.isNotBlank(avatarImgFastdfsPath)) {
						testDelImage(avatarImgFastdfsPath);
					}
					if (StringUtils.isNotBlank(coverImgFastdfsPath)) {
						testDelImage(coverImgFastdfsPath);
					}
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 3.爬取歌单关联的歌曲并插入歌曲数据到数据库
	 *
	 * @return
	 */
	public String insertSongs() {
		// 查询数据库中现有歌单的id
		List<BigInteger> listIds = crawlerService.getAllPlaylistId();
		JSONObject dataJson = null;
		JSONObject playlist = null;//歌单信息
		JSONArray tracks = null;//歌曲数组
		JSONObject jo = null;//歌曲对象
		JSONArray ar = null;//歌手数组
		JSONObject singerInfo = null; //歌手对象
		JSONArray aliaArray = null;//别名数组;
		JSONObject albumObject = null;//专辑对象
		JSONObject songUrlDataObject = null;//歌曲urlData
		JSONArray songUrlArray = null;//歌曲URL数组
		JSONObject songUrlObject = null;//歌曲URL对象
		JSONObject lrcObject = null;// 歌词对象
		String lrcStr =null;
		String songUrl = null;
		String fastDFSMp3Url = null;
		StringBuilder sb = new StringBuilder();//拼接歌手id
		StringBuilder sb2 = new StringBuilder();//拼接别名

		try {
			if (listIds.size() > 0 && listIds != null) {
				String resJson = null;
				String songUrlJson = null;
				for (int i = 0; i < listIds.size(); i++) {
					resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:3000/playlist/detail?id=" + listIds.get(i));
					PlaylistSong playlistSong = new PlaylistSong();
					playlistSong.setPlaylistId(listIds.get(i));
					dataJson = JSONObject.fromObject(resJson);
					if (dataJson != null && dataJson.getInt("code") == 200) {
						playlist = dataJson.getJSONObject("playlist");
						if (playlist != null) {
							tracks = playlist.getJSONArray("tracks");
							if (tracks.size() > 0 && tracks != null) {
								for (int j = 0; j < tracks.size(); j++) {
									jo = tracks.getJSONObject(j);
									playlistSong.setSongId(BigInteger.valueOf(jo.getLong("id")));
									if(!isPlaylistSongExists(playlistSong.getPlaylistId(),playlistSong.getSongId())){
										crawlerService.insertPlaylistSong(playlistSong);
									}

									if (isSongsExists(BigInteger.valueOf(jo.getLong("id")))) {
										log.info("=======歌曲已存在！继续插入下一歌曲=======");
										continue;
									}

									Songs songs = new Songs();//歌曲实体类
									songs.setSongId(new BigInteger(String.valueOf(jo.getLong("id"))));
									songs.setName(jo.getString("name"));
									ar = jo.getJSONArray("ar");
									// 仅插入歌手id，歌手信息等数据通过后面的insertSinger方法去爬取添加。
									if (ar != null && ar.size() > 0) {
										for (int i1 = 0; i1 < ar.size(); i1++) {
											singerInfo = ar.getJSONObject(i1);
											if (i1 == ar.size() - 1) {
												sb.append(singerInfo.getLong("id"));
											} else {
												sb.append(singerInfo.getLong("id") + ",");
											}
										}
										songs.setAvatorId(sb.toString());
										sb.delete(0, sb.length());
									}

									aliaArray = jo.getJSONArray("alia");
									if (aliaArray.size() > 0 && aliaArray != null) {
										for (int i1 = 0; i1 < aliaArray.size(); i1++) {
											if (i1 == aliaArray.size() - 1) {
												sb2.append(aliaArray.get(i1));
											} else {
												sb2.append(aliaArray.get(i1) + ",");
											}
										}
										songs.setAlia(sb2.toString());
										sb2.delete(0, sb2.length());
									}
									albumObject = jo.getJSONObject("al");
									songs.setAlbumId(BigInteger.valueOf(albumObject.getLong("id")));
									songs.setDateTime(jo.getInt("dt"));
									songs.setSongUrl("https://music.163.com/song/media/outer/url?id="+songs.getSongId());
									resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:3000/lyric?id=" + songs.getSongId());
									dataJson = JSONObject.fromObject(resJson);
									lrcObject = dataJson.getJSONObject("lrc");
									try {
										lrcStr = lrcObject.getString("lyric");
										songs.setLrc(lrcStr);
									} catch (Exception e) {
										log.error("==============>"+songs.getSongId()+"没有对应歌词<==============");
									} finally {
										crawlerService.insertSongs(songs);

									}

								}
							}
						}
					} else {
						log.error("接口数据不存在");
					}
				}


			} else {
				log.error("没有查到歌单ID");
			}
		} catch (Exception e) {
			log.error("======爬取歌单相关歌曲出错======");
			e.printStackTrace();
		}


		return null;
	}


	/**
	 * 4.爬取分类歌手机相关数据插入到数据库
	 *
	 * @return
	 */
	public String insertSingers() {
		List<Integer> list = crawlerService.getAllSingerCatIds();
		String resJson = null;
		JSONArray artists = null;
		JSONObject dataJson = null;
		String dataJson2 = null;
		JSONObject jo = null;
		JSONObject singerInfo = null;
		JSONObject aro = null;
		String dfsPath =null;
		for (int i = 0; i < list.size(); i++) {
			int o =0;
			while(true){
				resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:3000/artist/list?cat=" + list.get(i)+"&offset="+o);
				dataJson = JSONObject.fromObject(resJson);
				if(dataJson.getBoolean("more")==false){
					break;
				}
				artists = dataJson.getJSONArray("artists");
				for (int i1 = 0; i1 < artists.size(); i1++) {
					jo = artists.getJSONObject(i1);
					if(singerService.isSingerExists(BigInteger.valueOf(jo.getLong("id")))){
						log.info("该歌手已存在，继续下一个.");
						continue;
					}
					Singer singer = new Singer();
					singer.setId(BigInteger.valueOf(jo.getLong("id")));
					singer.setName(jo.getString("name"));
					dataJson2 = HttpClientUtils.doGetHtml("http://121.36.244.33:3000/artists?id=" + singer.getId());
					aro = JSONObject.fromObject(dataJson2);
					singerInfo = aro.getJSONObject("artist");
					singer.setAlbumSize(singerInfo.getInt("albumSize"));
					singer.setBriefDesc(singerInfo.getString("briefDesc"));
					singer.setMusicSize(singerInfo.getInt("musicSize"));
					try {
						dfsPath = download2UploadFile(singerInfo.getString("picUrl"), "jpg");
					} catch (Exception e) {
						log.error("下载图片并上传到fastDFS失败。");
						e.printStackTrace();
					}
					singer.setPicUrl(dfsPath);
					singer.setSingerType(list.get(i));
					singerService.insertSinger(singer);
				}
				o++;
			}
			log.info("歌手类别："+list.get(i)+"插入数据库完毕！");
			o=0;
		}
		return null;
	}

	/**
	 * 从歌曲表中查出所有的歌手id，插入数据库
	 */
	public String insertSingersFromSongs(){
		List<String> list = singerService.getSingerIdsFromSong();
		String dataJson2 =null;
		JSONObject aro = null;
		JSONObject dataObject = null;
		String dfsPath = null;
		for (int i = 0; i < list.size(); i++) {
			String[] singers = list.get(i).split(",");
			for (int i1 = 0; i1 < singers.length; i1++) {
				if(singerService.isSingerExists(new BigInteger(singers[i1]))){
					continue;
				}
				try {
					dataJson2 = HttpClientUtils.doGetHtml("http://121.36.244.33:3000/artists?id=" + singers[i1]);
					dataObject = JSONObject.fromObject(dataJson2);
					aro = dataObject.getJSONObject("artist");
					Singer singer = new Singer();
					singer.setId(BigInteger.valueOf(aro.getLong("id")));
					singer.setName(aro.getString("name"));
					singer.setAlbumSize(aro.getInt("albumSize"));
					singer.setBriefDesc(aro.getString("briefDesc"));
					singer.setMusicSize(aro.getInt("musicSize"));
					try {
						dfsPath = download2UploadFile(aro.getString("picUrl"), "jpg");
					} catch (Exception e) {
						log.error("下载图片并上传到fastDFS失败。");
						e.printStackTrace();
					}
					singer.setPicUrl(dfsPath);
					singerService.insertSinger(singer);
				} catch (Exception e) {
					log.error("获取参数失败！！");
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 5.爬取专辑数据插入到数据库
	 * @return
	 */
	public String insertAlbums(){
		// 从歌曲表查出所有关联专辑ID
		List<BigInteger> albumIds = albumService.getAlbumIdsFromSongs();
		if(albumIds!=null&&albumIds.size()>0){
			String resJson = null;
			JSONObject dataJson =null;
			JSONObject albumObject =null;
			String dfsPath = null;
			JSONArray alias =null;
			JSONObject artist =null;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < albumIds.size(); i++) {
				if(albumService.isAlbumExist(albumIds.get(i))){
					continue;
				}
				resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:3000/album?id=" + albumIds.get(i));
				if(StringUtils.isNotBlank(resJson)){
					dataJson = JSONObject.fromObject(resJson);
					albumObject = dataJson.getJSONObject("album");
					Album album = new Album();
					album.setId(albumIds.get(i));
					album.setName(albumObject.getString("name"));
					album.setDescription(albumObject.getString("description"));
					try {
						dfsPath = download2UploadFile(albumObject.getString("blurPicUrl"),"jpg");
					} catch (Exception e) {
						log.error("下载专辑图片失败！");
						e.printStackTrace();
					}
					album.setPicUrl(dfsPath);
					alias = albumObject.getJSONArray("alias");
					if(alias!=null&&alias.size()>0){
						for (int i1 = 0; i1 < alias.size(); i1++) {
							if(i1==alias.size()-1){
								sb.append(alias.get(i1));
							}else{
								sb.append(alias.get(i1)+",");
							}
						}
						album.setAlias(sb.toString());
						sb.delete(0,sb.length());
					}
					album.setSize(albumObject.getInt("size"));
					artist = albumObject.getJSONObject("artist");
					album.setSingerId(new BigInteger(String.valueOf(artist.getLong("id"))));
					albumService.insertAlbum(album);
				}
			}
		}
		return null;
	}



	/**
	 * 判断歌单和歌映射关系是否存在
	 * @param playlistId
	 * @param songId
	 * @return
	 */
	private boolean isPlaylistSongExists(BigInteger playlistId, BigInteger songId) {
		return crawlerService.isPlaylistSongExists(playlistId,songId);
	}


	/**
	 * 判断歌单是否存在
	 *
	 * @param id
	 * @return
	 */
	private boolean isPlaylistExists(BigInteger id) {
		return crawlerService.isPlaylistExists(id);
	}

	/**
	 * 判断用户是否存在
	 *
	 * @param id
	 * @return
	 */
	private boolean isUserExists(Long id) {
		return crawlerService.isUserExists(id);
	}

	/**
	 * 判断歌手是否存在
	 *
	 * @param id
	 * @return
	 */
	private boolean isSongsExists(BigInteger id) {
		return crawlerService.isSongsExists(id);
	}

	/**
	 * 解决网络下载图片直接传到fastdfs图片显示不完整的问题
	 *
	 * @param oldUrl 图片URL地址
	 * @return fastDFS的存储路径
	 * @throws Exception
	 */
	public String download2UploadFile(String oldUrl, String fileType) throws Exception {
		//1.下载图片
		File tempFile = null;
		InputStream is = null;
		StorePath storePath = null;
		try {
			tempFile = FileUtils.downloadFromUrl(oldUrl);
			/*if (oldUrl.contains(SPECIAL_SIGN)) {
				fileName = oldUrl.substring(oldUrl.lastIndexOf("/") + 1, oldUrl.indexOf(SPECIAL_SIGN));
			} else {
				fileName = oldUrl.substring(oldUrl.lastIndexOf("/") + 1);
			}
			//校验格式
			String suffix = fileName.substring(fileName.indexOf("."));
			if (!ImageContentType.toMap().containsKey(suffix)) {
				log.info("文件格式不对，该格式为={}", suffix);
				throw new ImageException("图片格式不对");
			}*/

			if (tempFile == null) {
				return "";
			}
			is = new FileInputStream(tempFile);
			if (is == null || is.available() <= 0) {
				return "";
			}
			//2.上传图片
			storePath = fastFileStorageClient.uploadFile(null, is, tempFile.length(), fileType);
		} finally {
			//手动删除临时文件
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new IOException("文件流关闭失败");
				}
			}
			if (tempFile != null) {
				tempFile.delete();
			}
		}
		log.info("上传完之后新图片地址=" + (storePath.getFullPath()));
		return storePath.getFullPath();
	}

	/**
	 * 测试fastdfs上传文件返回路径
	 *
	 * @return
	 */
	public String testDownloadAndUploadImage() {
		try {
			String s = download2UploadFile("https://m9.music.126.net/20200315105904/10151a80388d6985d3f5a0a39f455fc8/ymusic/075e/0f52/045c/0431c434b788a0a8a90f3658c6c0fd5f.mp3#128", "mp3");
			log.info("==================>");
			log.info(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 测试fastdfs上传文件返回路径,服务器写了个删除所有的脚本。使用那个即可。
	 *
	 * @return
	 */
	public String testDelImage(String fastDFSPath) {
		try {
			fastFileStorageClient.deleteFile(fastDFSPath);
			log.info("==================>fastdfs删除" + fastDFSPath + "成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("fastdfs删除" + fastDFSPath + "失败");
		}
		return null;
	}
}
