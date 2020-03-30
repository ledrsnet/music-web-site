package com.maple.music.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author LiangDong
 * @Date 2020/3/20
 */
@Entity
@Table(name = "m_singer", schema = "ssh_music")
public class Singer {
	private BigInteger id;
	private String name;
	private String picUrl;
	private Integer musicSize;
	private Integer albumSize;
	private String briefDesc;
	private Integer singerType;

	@Id
	@Column(name = "id")
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "pic_url")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Basic
	@Column(name = "music_size")
	public Integer getMusicSize() {
		return musicSize;
	}

	public void setMusicSize(Integer musicSize) {
		this.musicSize = musicSize;
	}

	@Basic
	@Column(name = "album_size")
	public Integer getAlbumSize() {
		return albumSize;
	}

	public void setAlbumSize(Integer albumSize) {
		this.albumSize = albumSize;
	}

	@Basic
	@Column(name = "brief_desc")
	public String getBriefDesc() {
		return briefDesc;
	}

	public void setBriefDesc(String briefDesc) {
		this.briefDesc = briefDesc;
	}


	@Basic
	@Column(name = "singer_type")
	public Integer getSingerType() {
		return singerType;
	}

	public void setSingerType(Integer singerType) {
		this.singerType = singerType;
	}


}
