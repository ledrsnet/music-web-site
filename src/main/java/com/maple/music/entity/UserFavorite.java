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
	private Long userId;
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
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserFavorite that = (UserFavorite) o;

		if (id != that.id) return false;
		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		return playlistId != null ? playlistId.equals(that.playlistId) : that.playlistId == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		result = 31 * result + (playlistId != null ? playlistId.hashCode() : 0);
		return result;
	}
}
