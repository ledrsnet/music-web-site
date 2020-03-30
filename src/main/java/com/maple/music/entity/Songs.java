package com.maple.music.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author LiangDong
 * @Date 2020/3/19
 */
@Entity
@Table(name = "m_Songs", schema = "ssh_music")
public class Songs {
	private BigInteger id;
	private BigInteger songId;
	private String name;
	private String avatorId;
	private String alia;
	private BigInteger albumId;
	private String avatorNames;
	private Integer dateTime;
	private String songUrl;
	private String lrc;
	private BigInteger playCount;
	private BigInteger oldPlayCount;
	private BigInteger lastWeekPlayCount;
	private Date createDate;
	private Date updateDate;

	@Id
	@Column(name = "id")
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	@Basic
	@Column(name = "song_id")
	public BigInteger getSongId() {
		return songId;
	}

	public void setSongId(BigInteger songId) {
		this.songId = songId;
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
	@Column(name = "avator_id")
	public String getAvatorId() {
		return avatorId;
	}

	public void setAvatorId(String avatorId) {
		this.avatorId = avatorId;
	}

	@Basic
	@Column(name = "avator_names")
	public String getAvatorNames() {
		return avatorNames;
	}

	public void setAvatorNames(String avatorNames) {
		this.avatorNames = avatorNames;
	}

	@Basic
	@Column(name = "alia")
	public String getAlia() {
		return alia;
	}

	public void setAlia(String alia) {
		this.alia = alia;
	}

	@Basic
	@Column(name = "album_id")
	public BigInteger getAlbumId() {
		return albumId;
	}

	public void setAlbumId(BigInteger albumId) {
		this.albumId = albumId;
	}

	@Basic
	@Column(name = "date_time")
	public Integer getDateTime() {
		return dateTime;
	}

	public void setDateTime(Integer dateTime) {
		this.dateTime = dateTime;
	}

	@Basic
	@Column(name = "song_url")
	public String getSongUrl() {
		return songUrl;
	}

	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}

	@Basic
	@Column(name = "lrc")
	public String getLrc() {
		return lrc;
	}

	public void setLrc(String lrc) {
		this.lrc = lrc;
	}

	@Basic
	@Column(name = "play_count")
	public BigInteger getPlayCount() {
		return playCount;
	}

	public void setPlayCount(BigInteger playCount) {
		this.playCount = playCount;
	}
	@Basic
	@Column(name = "old_play_count")
	public BigInteger getOldPlayCount() {
		return oldPlayCount;
	}

	public void setOldPlayCount(BigInteger oldPlayCount) {
		this.oldPlayCount = oldPlayCount;
	}
	@Basic
	@Column(name = "last_week_play_count")
	public BigInteger getLastWeekPlayCount() {
		return lastWeekPlayCount;
	}

	public void setLastWeekPlayCount(BigInteger lastWeekPlayCount) {
		this.lastWeekPlayCount = lastWeekPlayCount;
	}
	@Basic
	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Basic
	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
