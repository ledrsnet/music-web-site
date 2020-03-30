package com.maple.music.entity;

import javax.persistence.*;

/**
 * @author LiangDong
 * @Date 2020/3/20
 */
@Entity
@Table(name = "m_singer_type_config", schema = "ssh_music")
public class SingerTypeConfig {
	private int id;
	private Integer singerCode;
	private String singerText;
	private String singerTextC;

	@Id
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "singer_code")
	public Integer getSingerCode() {
		return singerCode;
	}

	public void setSingerCode(Integer singerCode) {
		this.singerCode = singerCode;
	}

	@Basic
	@Column(name = "singer_text")
	public String getSingerText() {
		return singerText;
	}

	public void setSingerText(String singerText) {
		this.singerText = singerText;
	}

	@Basic
	@Column(name = "singer_text_c")
	public String getSingerTextC() {
		return singerTextC;
	}

	public void setSingerTextC(String singerTextC) {
		this.singerTextC = singerTextC;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		SingerTypeConfig that = (SingerTypeConfig) o;

		if (id != that.id) return false;
		if (singerCode != null ? !singerCode.equals(that.singerCode) : that.singerCode != null) return false;
		if (singerText != null ? !singerText.equals(that.singerText) : that.singerText != null) return false;
		if (singerTextC != null ? !singerTextC.equals(that.singerTextC) : that.singerTextC != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (singerCode != null ? singerCode.hashCode() : 0);
		result = 31 * result + (singerText != null ? singerText.hashCode() : 0);
		result = 31 * result + (singerTextC != null ? singerTextC.hashCode() : 0);
		return result;
	}
}
