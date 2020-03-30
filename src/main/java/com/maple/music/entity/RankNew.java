package com.maple.music.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author LiangDong
 * @Date 2020/3/29
 */
@Entity
@Table(name = "m_rank_new", schema = "ssh_music")
public class RankNew {
	private int id;
	private BigInteger songId;
	private BigInteger playCount;
	private Date updateTime;
	private String rankType;

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
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
	@Column(name = "play_count")
	public BigInteger getPlayCount() {
		return playCount;
	}

	public void setPlayCount(BigInteger playCount) {
		this.playCount = playCount;
	}

	@Basic
	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Basic
	@Column(name = "rank_type")
	public String getRankType() {
		return rankType;
	}

	public void setRankType(String rankType) {
		this.rankType = rankType;
	}


}
