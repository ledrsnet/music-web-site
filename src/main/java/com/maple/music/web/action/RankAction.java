package com.maple.music.web.action;

import com.maple.music.entity.GuessRank;
import com.maple.music.entity.User;
import com.maple.music.service.RankService;
import com.maple.music.util.ResultUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/3/29
 */
@Controller("rankAction")
@Scope("prototype")
public class RankAction extends ActionSupport {

	@Resource
	private RankService rankService;

	/**
	 * 得到榜单头部信息
	 *
	 * @return
	 */
	public String getRankInfoByType() {
		String rankType = ServletActionContext.getRequest().getParameter("rank_type");
		Map<String, Object> map = new HashMap<>();
		try {
			map = rankService.getRankInfo(rankType);
			if (!map.isEmpty()) {
				map.put("code", 200);
				ResultUtils.toJson(ServletActionContext.getResponse(), map);
			} else {
				map.put("code", 500);
				ResultUtils.toJson(ServletActionContext.getResponse(), map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到歌手榜列表展示信息
	 *
	 * @return
	 */
	public String getSingerRankInfo() {
		String ids = ServletActionContext.getRequest().getParameter("ids");
		String type = ServletActionContext.getRequest().getParameter("type");
		Map<String, Object> map = new HashMap<>();
		try {
			if (StringUtils.isNotBlank(ids)) {
				map = rankService.getSingerRankInfoByIds(ids);
			} else if (StringUtils.isNotBlank(type)) {
				map = rankService.getSingerRankInfoByType(type);
			}
			if (!map.isEmpty()) {
				map.put("code", 200);
				ResultUtils.toJson(ServletActionContext.getResponse(), map);
			} else {
				map.put("code", 500);
				ResultUtils.toJson(ServletActionContext.getResponse(), map);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取其他榜列表展示信息
	 *
	 * @return
	 */
	public String getSongForRank() {
		Map<String, Object> map = new HashMap<>();
		String rank_type = ServletActionContext.getRequest().getParameter("rank_type");
		try {
			map = rankService.getSongsByType(rank_type);
			ResultUtils.toJson(ServletActionContext.getResponse(), map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取猜歌榜数据
	 *
	 * @return
	 */
	public String getGuessRank() {
		Map<String, Object> map = new HashMap<>();
		try {
			map = rankService.getGuessRank();
			if (map.isEmpty()) {
				map.put("code", 400);
				map.put("errMsg", "数据为空！");
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
	 * 获取猜歌的数据
	 *
	 * @return
	 */
	public String getGuessSongsInfo() {
		Map<String, Object> map = new HashMap<>();
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		try {
			if (user != null) {
				map = rankService.getGuessSongsInfo();
				if (map.isEmpty()) {
					map.put("code", 500);
					map.put("errMsg", "获取歌曲数据失败");
				} else {
					map.put("code", 200);
				}
			} else {
				map.put("code", 500);
				map.put("errMsg", "猜歌前请先登录");
			}
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加闯关数据
	 *
	 * @return
	 */
	public String addGuessRankInfo() {
		Map<String, Object> map = new HashMap<>();
		User user = (User) ServletActionContext.getRequest().getSession().getAttribute("user");
		int obNum = Integer.parseInt(ServletActionContext.getRequest().getParameter("obNum"));
		try {
			if (user != null) {
				int i = 0;
				boolean flag = rankService.isInsertGuessRank(BigInteger.valueOf(user.getUserId()),obNum);
				if(flag){
					GuessRank guessRank = new GuessRank();
					guessRank.setCount(obNum);
					guessRank.setUserId(BigInteger.valueOf(user.getUserId()));
					guessRank.setCreateTime(new Date());
					guessRank.setUpdateTime(new Date());
					i = rankService.addGuessRankInfo(guessRank);
					if (i == 0) {
						map.put("code", 500);
						map.put("errMsg", "闯关数据上传失败!");
					} else {
						map.put("code", 200);
						map.put("errMsg", "闯关数据上传成功");
					}
				}else{
					map.put("code",500);
					map.put("errMsg","未打破用户个人记录，本次闯关将不记录");
				}


			} else {
				map.put("code", 500);
				map.put("errMsg", "用户尚未登录，请先登录");
			}
			ResultUtils.toJson(ServletActionContext.getResponse(), map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}