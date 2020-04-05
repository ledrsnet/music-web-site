package com.maple.music.web.action;

import com.maple.music.entity.User;
import com.maple.music.entity.UserFavorite;
import com.maple.music.service.PlaylistsService;
import com.maple.music.util.ResultUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/14
 */
@Controller("playlistsAction")
@Scope("prototype")
public class PlaylistsAction extends ActionSupport {

	private static final Logger log = LoggerFactory.getLogger(PlaylistsAction.class);

	// 歌单ID
	private BigInteger id;
	// 歌曲标识
	private String ids;
	// 分类标识
	private String cat;
	// 最热or最新
	private String order;

	public void setCat(String cat) {
		this.cat = cat;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	@Resource
	private PlaylistsService playlistsService;


	/**
	 * 获取歌单分类数据
	 *
	 * @return
	 */
	public String getCategories() {
		Map<String, Object> categoriesInfo = new HashMap<>();
		try {
			categoriesInfo = playlistsService.getCategoriesInfo();
			categoriesInfo.put("code", 200);
			ResultUtils.toJson(ServletActionContext.getResponse(), categoriesInfo);
		} catch (Exception e) {
			e.printStackTrace();
			categoriesInfo.put("code", 500);
			try {
				ResultUtils.toJson(ServletActionContext.getResponse(), categoriesInfo);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取歌单列表
	 *
	 * @return
	 */
	public String getPlaylists() {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list;
		if (StringUtils.isNotBlank(cat) && StringUtils.isNotBlank(order)) {
			list = playlistsService.getPlaylists(cat, order);
		} else {
			list = playlistsService.getPlaylists();
		}
		map.put("playlists", list);
		map.put("code", list != null ? 200 : 500);
		try {
			ResultUtils.toJson(ServletActionContext.getResponse(), map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 获取歌单详情
	 *
	 * @return
	 */
	public String getPlaylistDetail() {
		Map<String, Object> map = new HashMap<>();
		try {
			map = playlistsService.getPlaylistDetail(id);
			map.put("code", 200);
			ResultUtils.toJson(ServletActionContext.getResponse(), map);
		} catch (Exception e) {
			log.error("获取歌单详情失败！！！！！！！！！！");
			try {
				ResultUtils.toJson(ServletActionContext.getResponse(), map.put("code", 500));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取歌单里所有歌曲信息
	 *
	 * @return
	 */
	public String getPlaySongs() {
		Map<String, Object> map = new HashMap<>();
		try {
			map = playlistsService.getSongsByIds(ids);
			ResultUtils.toJson(ServletActionContext.getResponse(), map);
		} catch (Exception e) {
			log.error("获取歌单详情失败！！！！！！！！！！");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取推荐歌单
	 *
	 * @return
	 */
	public String getReCommendPlaylist() {
		Map<String, Object> map = new HashMap<>();
		try {
			map = playlistsService.getReCommendPlaylist();
			if (!map.isEmpty()) {
				map.put("code", 200);
			} else {
				map.put("code", 500);
			}
			ResultUtils.toJson(ServletActionContext.getResponse(), map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取指定歌单的评论
	 *
	 * @return
	 */
	public String getCommentForPlaylist() {
		String id = ServletActionContext.getRequest().getParameter("id");
		Map<String, Object> map = new HashMap<>();
		try {
			map = playlistsService.getCommentForPlaylist(id);
			if (map.isEmpty()) {
				map.put("code", 400);
			} else {
				map.put("code", 200);
			}
			ResultUtils.toJson(ServletActionContext.getResponse(), map);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 添加歌单评论
	 * @return
	 */
	public String addComment() {
		Map<String, Object> map = new HashMap<>();
		String txt = ServletActionContext.getRequest().getParameter("txt");
		String playlistId = ServletActionContext.getRequest().getParameter("playlistId");
		User user = (User) ActionContext.getContext().getSession().get("user");
		if (user == null) {
			map.put("code", 400);
			map.put("errMsg", "用户没有登录");
		} else {
			int i = playlistsService.addComment(txt, new BigInteger(playlistId), user.getUserId());
			if (i > 0) {
				map.put("code", 200);
				map.put("errMsg", "添加成功");
			} else {
				map.put("code", 500);
				map.put("errMsg", "未知错误！添加失败");
			}
		}
		try {
			ResultUtils.toJson(ServletActionContext.getResponse(), map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 收藏歌单
	 * @return
	 */
	public String addFavorite(){
		String id = ServletActionContext.getRequest().getParameter("id");
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		Map<String,Object> map = new HashMap<>();
		if(user==null){
			map.put("code", 400);
			map.put("errMsg", "用户没有登录");
		}else{
			UserFavorite userFavorite = new UserFavorite();
			userFavorite.setPlaylistId(new BigInteger(id));
			userFavorite.setUserId(BigInteger.valueOf(user.getUserId()));
			int i = playlistsService.addFavorite(userFavorite);
			if (i > 0) {
				map.put("code", 200);
				map.put("errMsg", "收藏成功");
			} else {
				map.put("code", 500);
				map.put("errMsg", "未知错误！收藏失败");
			}
		}
		try {
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断用户是否收藏歌单
	 * @return
	 */
	public String isFavoritePlaylist(){
		Map<String,Object> map = new HashMap<>();
		try {
			User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
			if(user!=null){
				String playlistId = ServletActionContext.getRequest().getParameter("id");
				boolean flag = playlistsService.isFavoritePlaylist(BigInteger.valueOf(user.getUserId()),new BigInteger(playlistId));
				map.put("flag",flag);
			}else{
				map.put("flag",false);
			}
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	};
}
