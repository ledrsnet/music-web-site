package com.maple.music.entity;

import java.math.BigInteger;
import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/24
 */
public class SongsDto {
	private BigInteger id;
	private Long songId;
	private String name;
	private String avatorId;
	private List<Singer> singers;
	private String[] alia;
	private Long albumId;
	private Album album;
	private Integer dateTime;
	private String songUrl;
	private String lrc;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Long getSongId() {
		return songId;
	}

	public void setSongId(Long songId) {
		this.songId = songId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatorId() {
		return avatorId;
	}

	public void setAvatorId(String avatorId) {
		this.avatorId = avatorId;
	}

	public List<Singer> getSingers() {
		return singers;
	}

	public void setSingers(List<Singer> singers) {
		this.singers = singers;
	}

	public String[] getAlia() {
		return alia;
	}

	public void setAlia(String[] alia) {
		this.alia = alia;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Integer getDateTime() {
		return dateTime;
	}

	public void setDateTime(Integer dateTime) {
		this.dateTime = dateTime;
	}

	public String getSongUrl() {
		return songUrl;
	}

	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}

	public String getLrc() {
		return lrc;
	}

	public void setLrc(String lrc) {
		this.lrc = lrc;
	}
}
