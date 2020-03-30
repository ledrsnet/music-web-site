package com.maple.music.web.action;

import com.maple.music.service.RankService;
import com.maple.music.util.ResultUtils;
import com.opensymphony.xwork2.ActionSupport;
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
 * @Date 2020/3/29
 */
@Controller("rankAction")
@Scope("prototype")
public class RankAction  extends ActionSupport {

	@Resource
	private RankService rankService;

	/**
	 * 得到榜单头部信息
	 * @return
	 */
	public String getRankInfoByType(){
		String rankType = ServletActionContext.getRequest().getParameter("rank_type");
		Map<String,Object> map = new HashMap<>();
		try {
			map = rankService.getRankInfo(rankType);
			if(!map.isEmpty()){
				map.put("code",200);
				ResultUtils.toJson(ServletActionContext.getResponse(),map);
			}else{
				map.put("code",500);
				ResultUtils.toJson(ServletActionContext.getResponse(),map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到歌手榜列表展示信息
	 * @return
	 */
	public String getSingerRankInfo(){
		String ids = ServletActionContext.getRequest().getParameter("ids");
		String type = ServletActionContext.getRequest().getParameter("type");
		Map<String,Object> map = new HashMap<>();
		try {
			if(StringUtils.isNotBlank(ids)){
				map = rankService.getSingerRankInfoByIds(ids);
			}else if(StringUtils.isNotBlank(type)){
				map = rankService.getSingerRankInfoByType(type);
			}
			if(!map.isEmpty()){
				map.put("code",200);
				ResultUtils.toJson(ServletActionContext.getResponse(),map);
			}else{
				map.put("code",500);
				ResultUtils.toJson(ServletActionContext.getResponse(),map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取其他榜列表展示信息
	 * @return
	 */
	public String getSongForRank(){
		Map<String,Object> map = new HashMap<>();
		String rank_type = ServletActionContext.getRequest().getParameter("rank_type");
		try {
			map = rankService.getSongsByType(rank_type);
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
