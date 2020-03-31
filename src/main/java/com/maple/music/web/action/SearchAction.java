package com.maple.music.web.action;

import com.maple.music.service.SearchService;
import com.maple.music.util.ResultUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/30
 */
@Controller("searchAction")
@Scope("prototype")
public class SearchAction {

	@Resource
	private SearchService searchService;
	/**
	 * 搜索相关
	 * @return
	 */
	public String search(){
		// type:100歌手，10专辑，1000歌单，null是单曲
		String type = ServletActionContext.getRequest().getParameter("type");
		String keywords = ServletActionContext.getRequest().getParameter("keywords");
		Map<String,Object> map = new HashMap<>();
		try {
			if(StringUtils.isBlank(keywords)){
				map.put("code",501);
				map.put("errMsg","参数为空");
			}else{
				if(StringUtils.isBlank(type)){
					map = searchService.searchSingleSongByKey(keywords);
				}else if(StringUtils.isNotBlank(type)&&Integer.valueOf(type)==10){
					map = searchService.searchAlbumByKey(keywords);
				}else if(StringUtils.isNotBlank(type)&&Integer.valueOf(type)==100){
					map = searchService.searchSingerByKey(keywords);
				}else if(StringUtils.isNotBlank(type)&&Integer.valueOf(type)==1000){
					map = searchService.searchPlaylistByKey(keywords);
				}
				if(map.isEmpty()){
					map.put("code",404);
				}else{
					map.put("code",200);
				}
			}
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
