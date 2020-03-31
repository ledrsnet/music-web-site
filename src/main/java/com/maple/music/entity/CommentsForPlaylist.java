package com.maple.music.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author LiangDong
 * @Date 2020/3/31
 */
@Entity
@Table(name = "m_comments_for_playlist", schema = "ssh_music")
public class CommentsForPlaylist {
	private long id;
	private BigInteger commentsId;
	private Long userId;
	private String content;
	private Date createTime;
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
	@Column(name = "comments_id")
	public BigInteger getCommentsId() {
		return commentsId;
	}

	public void setCommentsId(BigInteger commentsId) {
		this.commentsId = commentsId;
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
	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Basic
	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
