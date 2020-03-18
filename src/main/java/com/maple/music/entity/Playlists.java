package com.maple.music.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author LiangDong
 * @Date 2020/3/14
 */
@Entity
@Table(name = "m_playlists", schema = "ssh_music")
public class Playlists {
	private long id;
	private String name;
	private Long userId;
	private Date createTime;
	private Date updateTime;
	private Long subscribedCount;
	private Integer trackCount;
	private String coverImgUrl;
	private String description;
	private Long playCount;
	private String tags;

	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
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
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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
	public Long getSubscribedCount() {
		return subscribedCount;
	}

	public void setSubscribedCount(Long subscribedCount) {
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
	public Long getPlayCount() {
		return playCount;
	}

	public void setPlayCount(Long playCount) {
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


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Playlists that = (Playlists) o;

		if (id != that.id) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
		if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
		if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null) return false;
		if (subscribedCount != null ? !subscribedCount.equals(that.subscribedCount) : that.subscribedCount != null)
			return false;
		if (trackCount != null ? !trackCount.equals(that.trackCount) : that.trackCount != null) return false;
		if (coverImgUrl != null ? !coverImgUrl.equals(that.coverImgUrl) : that.coverImgUrl != null) return false;
		if (description != null ? !description.equals(that.description) : that.description != null) return false;
		if (playCount != null ? !playCount.equals(that.playCount) : that.playCount != null) return false;
		if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (userId != null ? userId.hashCode() : 0);
		result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
		result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
		result = 31 * result + (subscribedCount != null ? subscribedCount.hashCode() : 0);
		result = 31 * result + (trackCount != null ? trackCount.hashCode() : 0);
		result = 31 * result + (coverImgUrl != null ? coverImgUrl.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + (playCount != null ? playCount.hashCode() : 0);
		result = 31 * result + (tags != null ? tags.hashCode() : 0);
		return result;
	}
}
