package com.maple.music.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * @author LiangDong
 * @Date 2020/3/22
 */
@Entity
@Table(name = "m_playlist_song", schema = "ssh_music")
public class PlaylistSong implements Serializable {
	private BigInteger id;
	private BigInteger playlistId;
	private BigInteger songId;

	public PlaylistSong() {
	}

	@Id
	@Column(name = "id")
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}
	@Basic
	@Column(name = "playlist_id")
	public BigInteger getPlaylistId() {
		return playlistId;
	}
	@Basic
	public void setPlaylistId(BigInteger playlistId) {
		this.playlistId = playlistId;
	}

	@Column(name = "song_id")
	public BigInteger getSongId() {
		return songId;
	}

	public void setSongId(BigInteger songId) {
		this.songId = songId;
	}

}
