package com.maple.music.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author LiangDong
 * @Date 2020/3/14
 */
@Entity
@Table(name = "m_playlists", schema = "ssh_music")
public class Playlists {
	private BigInteger id;
	private String name;
	private BigInteger userId;
	private Date createTime;
	private Date updateTime;
	private BigInteger subscribedCount;
	private Integer trackCount;
	private String coverImgUrl;
	private String description;
	private BigInteger playCount;
	private BigInteger oldPlayCount;
	private String tags;
	private String tagsText;

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
	@Column(name = "user_id")
	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
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
	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Basic
	@Column(name = "subscribed_count")
	public BigInteger getSubscribedCount() {
		return subscribedCount;
	}

	public void setSubscribedCount(BigInteger subscribedCount) {
		this.subscribedCount = subscribedCount;
	}

	@Basic
	@Column(name = "track_count")
	public Integer getTrackCount() {
		return trackCount;
	}

	public void setTrackCount(Integer trackCount) {
		this.trackCount = trackCount;
	}

	@Basic
	@Column(name = "cover_img_url")
	public String getCoverImgUrl() {
		return coverImgUrl;
	}

	public void setCoverImgUrl(String coverImgUrl) {
		this.coverImgUrl = coverImgUrl;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	@Column(name = "tags")
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Basic
	@Column(name = "tags_text")
	public String getTagsText() {
		return tagsText;
	}

	public void setTagsText(String tagsText) {
		this.tagsText = tagsText;
	}
	@Basic
	@Column(name = "old_play_count")
	public BigInteger getOldPlayCount() {
		return oldPlayCount;
	}

	public void setOldPlayCount(BigInteger oldPlayCount) {
		this.oldPlayCount = oldPlayCount;
	}
}
