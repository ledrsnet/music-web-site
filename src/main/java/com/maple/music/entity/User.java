package com.maple.music.entity;

import javax.persistence.*;
import java.sql.Timestamp;

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
	private Timestamp creatTime;
	private Timestamp lastTime;
	private Byte state;

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
	public Timestamp getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Timestamp creatTime) {
		this.creatTime = creatTime;
	}

	@Basic
	@Column(name = "last_time")
	public Timestamp getLastTime() {
		return lastTime;
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}

	@Basic
	@Column(name = "state")
	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User User = (User) o;

		if (id != User.id) return false;
		if (username != null ? !username.equals(User.username) : User.username != null) return false;
		if (password != null ? !password.equals(User.password) : User.password != null) return false;
		if (nickname != null ? !nickname.equals(User.nickname) : User.nickname != null) return false;
		if (creatTime != null ? !creatTime.equals(User.creatTime) : User.creatTime != null) return false;
		if (lastTime != null ? !lastTime.equals(User.lastTime) : User.lastTime != null) return false;
		if (state != null ? !state.equals(User.state) : User.state != null) return false;

		return true;
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
		return result;
	}
}
