package com.maple.music.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * @author LiangDong
 * @Date 2019/11/20
 */
@Entity
@Table(name = "m_user", schema = "ssh_music")
public class User {
	private long id;
	private long userId;
	private String username;
	private String password;
	private String nickname;
	private Date creatTime;
	private Date lastTime;
	private Integer state;
	private String emailAddress;
	private String signature;
	private String avatarUrl;
	private Integer gender;

	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "userId")
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Basic
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Basic
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "nickname")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Basic
	@Column(name = "creat_time")
	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	@Basic
	@Column(name = "last_time")
	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	@Basic
	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Basic
	@Column(name = "emailAddress")
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	@Basic
	@Column(name = "signature")
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Basic
	@Column(name = "avatar_url")
	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	@Basic
	@Column(name = "gender")
	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		if (userId != user.userId) return false;
		if (username != null ? !username.equals(user.username) : user.username != null) return false;
		if (password != null ? !password.equals(user.password) : user.password != null) return false;
		if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null) return false;
		if (creatTime != null ? !creatTime.equals(user.creatTime) : user.creatTime != null) return false;
		if (lastTime != null ? !lastTime.equals(user.lastTime) : user.lastTime != null) return false;
		if (state != null ? !state.equals(user.state) : user.state != null) return false;
		if (emailAddress != null ? !emailAddress.equals(user.emailAddress) : user.emailAddress != null) return false;
		if (signature != null ? !signature.equals(user.signature) : user.signature != null) return false;
		if (avatarUrl != null ? !avatarUrl.equals(user.avatarUrl) : user.avatarUrl != null) return false;
		return gender != null ? gender.equals(user.gender) : user.gender == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (int) (userId ^ (userId >>> 32));
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
		result = 31 * result + (creatTime != null ? creatTime.hashCode() : 0);
		result = 31 * result + (lastTime != null ? lastTime.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
		result = 31 * result + (signature != null ? signature.hashCode() : 0);
		result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
		result = 31 * result + (gender != null ? gender.hashCode() : 0);
		return result;
	}
}
