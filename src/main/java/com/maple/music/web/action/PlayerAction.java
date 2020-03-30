package com.maple.music.web.action;

import com.maple.music.entity.PlayerVo;
import com.maple.music.service.PlayerService;
import com.maple.music.service.PlaylistsService;
import com.maple.music.util.ResultUtils;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/27
 */
@Controller("playerAction")
@Scope("prototype")
public class PlayerAction extends ActionSupport {
	private static final Logger log = LoggerFactory.getLogger(PlayerAction.class);

	@Resource
	private PlayerService playerService;

	private String type;
	private BigInteger id;
	private BigInteger songId;

	public void setType(String type) {
		this.type = type;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public void setSongId(BigInteger songId) {
		this.songId = songId;
	}

	public String playerApi(){
		List<PlayerVo> list = new ArrayList<>();
		if(StringUtils.isNotBlank(type)&&"playlist".equals(type)){
			list = playerService.getSongsByPlaylistId(id);
		}else if(StringUtils.isNotBlank(type)&&"song".equals(type)){
			list = playerService.getSongsBySongId(id);
		}else  if(StringUtils.isNotBlank(type)&&"singer".equals(type)){
			list = playerService.getSongsBySingerId(id);
		}else if(StringUtils.isNotBlank(type)&&"rank".equals(type)){
			// 排行榜页面播放音乐所需数据
			if(id.compareTo(BigInteger.valueOf(111))==0){
				list = playerService.getSongsByRank("new");
			}else if(id.compareTo(BigInteger.valueOf(222))==0){
				list = playerService.getSongsByRank("soar");
			}else if(id.compareTo(BigInteger.valueOf(333))==0){
				list = playerService.getSongsByRank("hot");
			}
		}
		try {
			ResultUtils.toJson(ServletActionContext.getResponse(),list);
		} catch (IOException e) {
			log.error("播放器获取播放数据失败！");
			e.printStackTrace();
		}
		return null;
	}

	//返回歌曲数据
	public String lrc(){
		PrintWriter writer = null;
		//解决response输出乱码
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		ServletActionContext.getResponse().setContentType("text/plain;charset=utf-8");
		try {
			writer = ServletActionContext.getResponse().getWriter();
			String lrc = playerService.getLrcBySongId(songId);
			writer.print(lrc);
		} catch (IOException e) {
			writer.print("歌词信息获取失败！");
			e.printStackTrace();
		}
		return null;
	}
}
