package com.maple.music.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author LiangDong
 * @Date 2020/3/28
 */
@Entity
@Table(name = "m_user_favorite", schema = "ssh_music")
public class UserFavorite {
	private long id;
	private BigInteger userId;
	private BigInteger playlistId;

	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Basic
	@Column(name = "user_id")
	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "playlist_id")
	public BigInteger getPlaylistId() {
		return playlistId;
	}

	public void setPlaylistId(BigInteger playlistId) {
		this.playlistId = playlistId;
	}


}
