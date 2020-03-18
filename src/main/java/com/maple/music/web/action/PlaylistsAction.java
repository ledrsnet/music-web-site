package com.maple.music.web.action;

import com.maple.music.service.PlaylistsService;
import com.maple.music.util.ResultUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
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

	@Resource
	private PlaylistsService playlistsService;


	/**
	 * 获取歌单分类数据
	 * @return
	 */
	public String getCategories(){
		Map<String,Object> categoriesInfo = new HashMap<>();
		try {
			categoriesInfo = playlistsService.getCategoriesInfo();
			categoriesInfo.put("code",200);
			ResultUtils.toJson(ServletActionContext.getResponse(),categoriesInfo);
		} catch (Exception e) {
			e.printStackTrace();
			categoriesInfo.put("code",500);
			try {
				ResultUtils.toJson(ServletActionContext.getResponse(),categoriesInfo);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 获取歌单列表
	 * @return
	 */
	public String getPlaylists(){
		Map<String,Object> map = new HashMap<>();
		List<Map<String,Object>> list;
		if(StringUtils.isNotBlank(cat)&& StringUtils.isNotBlank(order)){
			list = playlistsService.getPlaylists(cat,order);
		}else{
			list = playlistsService.getPlaylists();
		}
		map.put("playlists",list);
		map.put("code",list!=null?200:500);
		try {
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
