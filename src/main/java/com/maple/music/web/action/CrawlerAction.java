package com.maple.music.web.action;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.maple.music.entity.CategoriesConfig;
import com.maple.music.entity.Playlists;
import com.maple.music.entity.User;
import com.maple.music.service.CrawlerService;
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

/**
 * @author LiangDong
 * @Date 2020/3/2
 */
@Controller("crawlerAction")
@Scope("prototype")
public class CrawlerAction  extends ActionSupport {
	public static final Logger log = LoggerFactory.getLogger(CrawlerAction.class);
	public static final String FASTDFS_PATH= "http://121.36.244.33:8080/";

	@Resource
	private CrawlerService crawlerService;

	@Resource
	private UserService userService;

	@Resource
	private FastFileStorageClient fastFileStorageClient;




	/**
	 * 爬取歌单分类数据写入数据库
	 * @return
	 */
	public String insertCateGories(){
		try {
			String resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:3000/playlist/catlist");
			JSONObject dataJson=JSONObject.fromObject(resJson);
			JSONArray sub=dataJson.getJSONArray("sub");
			PrintWriter writer = null;
			//解决response输出乱码
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			writer = ServletActionContext.getResponse().getWriter();
			if (sub == null) {
				writer.println("爬取分类数据为空！");
			}else{
				try {
					for (int i = 0; i < sub.size(); i++) {
						JSONObject  o = (JSONObject) sub.get(i);
						CategoriesConfig cat = new CategoriesConfig();
						cat.setName(o.getString("name"));
						cat.setCategory(o.getInt("category"));
						boolean flag = o.getBoolean("hot");
						if(flag==true){
							cat.setHot(1);
						}else{
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
	 * 爬取歌单数据插入数据库
	 * @return
	 */
	public String insertPlaylists(){
		try {
			String resJson = HttpClientUtils.doGetHtml("http://121.36.244.33:3000/top/playlist?limit=1301");
			JSONObject dataJson=JSONObject.fromObject(resJson);
			JSONArray playlists=dataJson.getJSONArray("playlists");
			PrintWriter writer = null;
			//解决response输出乱码
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			writer = ServletActionContext.getResponse().getWriter();
			if (playlists == null) {
				writer.println("爬取分类数据为空！");
			}else{
				JSONObject o=null;
				JSONObject creator=null;
				String coverImgFastdfsPath=null;
				String avatarImgFastdfsPath=null;
				try {
					for (int i = 0; i < playlists.size(); i++) {
						 o = (JSONObject) playlists.get(i);
						if(isPlaylistExists(o.getLong("id"))){
							log.info("歌单已存在，继续爬取下一个.");
							continue;
						}
						Playlists lists = new Playlists();
						lists.setId(o.getLong("id"));
						lists.setName(o.getString("name"));
						lists.setUserId(o.getLong("userId"));
						lists.setCreateTime(ConversionUtils.stampToDate(o.getLong("createTime")));
						lists.setUpdateTime(ConversionUtils.stampToDate(o.getLong("updateTime")));
						lists.setSubscribedCount(o.getLong("subscribedCount"));
						lists.setTrackCount(o.getInt("trackCount"));

						// =====下载图片上传到fastdfs=====
						String coverImgUrl = o.getString("coverImgUrl");
						coverImgFastdfsPath = download2UploadFile(coverImgUrl,"jpg");
						// 存储fastdfs的地址
						lists.setCoverImgUrl(coverImgFastdfsPath);
						lists.setDescription(o.getString("description"));
						// =====fastdfs存储完成=====

						// 设置歌单的标签
						JSONArray tags = o.getJSONArray("tags");
						StringBuilder tagsStr= new StringBuilder();
						for (int j = 0; j < tags.size(); j++) {
							Integer categoryId = crawlerService.getCategoryId((String) tags.get(j));
							if(categoryId==null){
								continue;
							}
							if(j==tags.size()-1){
								tagsStr.append(categoryId);
							}else{
								tagsStr.append(categoryId);
								tagsStr.append(",");
							}
						}
						lists.setTags(tagsStr.toString());
						// 设置歌单标签完成

						lists.setPlayCount(o.getLong("playCount"));

						// 新增用户
						creator = o.getJSONObject("creator");
						if(isUserExists(creator.getLong("userId"))){
							log.info("用户已存在，继续爬取下一个.");
							continue;
						}
						User user = new User();
						user.setUserId(creator.getLong("userId"));
						user.setUsername("test"+i);
						user.setPassword(MD5.encryptPassword("redhat","123"));
						user.setNickname(creator.getString("nickname"));
						user.setCreatTime(ConversionUtils.stampToDate(1521047305000L));
						user.setLastTime(ConversionUtils.stampToDate(1521047305000L));
						// =====下载图片上传到fastdfs=====
						String avatarImgUrl = creator.getString("avatarUrl");
						avatarImgFastdfsPath = download2UploadFile(avatarImgUrl,"jpg");
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
					if(StringUtils.isNotBlank(avatarImgFastdfsPath)){
						testDelImage(avatarImgFastdfsPath);
					}
					if(StringUtils.isNotBlank(coverImgFastdfsPath)){
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

	private boolean isPlaylistExists(long id) {
		return crawlerService.isPlaylistExists(id);
	}
	private boolean isUserExists(long id) {
		return crawlerService.isUserExists(id);
	}

	/**
	 * 解决网络下载图片直接传到fastdfs图片显示不完整的问题
	 * @param oldUrl 图片URL地址
	 * @return fastDFS的存储路径
	 * @throws Exception
	 */
	public String download2UploadFile(String oldUrl,String fileType) throws Exception {
		//1.下载图片
		File tempFile = null;
		InputStream is = null;
		StorePath storePath= null;
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

			if (tempFile == null ) {
				return "";
			}
			is = new FileInputStream(tempFile);
			if(is == null || is.available() <= 0){
				return "";
			}
			//2.上传图片
			storePath = fastFileStorageClient.uploadFile(null,is,tempFile.length(),fileType);
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
		log.info("上传完之后新图片地址="+(storePath.getFullPath()));
		return storePath.getFullPath();
	}

	/**
	 * 测试fastdfs上传文件返回路径
	 * @return
	 */
	public String testDownloadAndUploadImage(){
		try {
			String s = download2UploadFile("https://m9.music.126.net/20200315105904/10151a80388d6985d3f5a0a39f455fc8/ymusic/075e/0f52/045c/0431c434b788a0a8a90f3658c6c0fd5f.mp3#128","mp3");
			log.info("==================>");
			log.info(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 测试fastdfs上传文件返回路径,服务器写了个删除所有的脚本。使用那个即可。
	 * @return
	 */
	public String testDelImage(String fastDFSPath){
		try {
			fastFileStorageClient.deleteFile(fastDFSPath);
			log.info("==================>fastdfs删除"+fastDFSPath+"成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("fastdfs删除"+fastDFSPath+"失败");
		}
		return null;
	}
}
