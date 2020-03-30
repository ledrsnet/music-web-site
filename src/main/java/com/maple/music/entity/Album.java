package com.maple.music.entity;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * @author LiangDong
 * @Date 2020/3/23
 */
@Entity
@Table(name = "m_album", schema = "ssh_music")
public class Album {
	private BigInteger id;
	private String name;
	private String description;
	private String picUrl;
	private String alias;
	private Integer size;
	private BigInteger singerId;

	public Album() {
	}

	public Album(BigInteger id, String name) {
		this.id=id;
		this.name=name;
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
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	@Column(name = "pic_url")
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Basic
	@Column(name = "alias")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Basic
	@Column(name = "size")
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	@Basic
	@Column(name = "singer_id")
	public BigInteger getSingerId() {
		return singerId;
	}

	public void setSingerId(BigInteger singerId) {
		this.singerId = singerId;
	}


}
