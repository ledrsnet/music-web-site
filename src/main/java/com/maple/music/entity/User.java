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
	private String username;
	private String password;
	private String nickname;
	private Date creatTime;
	private Date lastTime;
	private Integer state;
	private String emailAddress;

	@Id
	@Column(name = "id")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		if (username != null ? !username.equals(user.username) : user.username != null) return false;
		if (password != null ? !password.equals(user.password) : user.password != null) return false;
		if (nickname != null ? !nickname.equals(user.nickname) : user.nickname != null) return false;
		if (creatTime != null ? !creatTime.equals(user.creatTime) : user.creatTime != null) return false;
		if (lastTime != null ? !lastTime.equals(user.lastTime) : user.lastTime != null) return false;
		if (state != null ? !state.equals(user.state) : user.state != null) return false;
		return emailAddress != null ? emailAddress.equals(user.emailAddress) : user.emailAddress == null;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
		result = 31 * result + (creatTime != null ? creatTime.hashCode() : 0);
		result = 31 * result + (lastTime != null ? lastTime.hashCode() : 0);
		result = 31 * result + (state != null ? state.hashCode() : 0);
		result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
		return result;
	}
}
