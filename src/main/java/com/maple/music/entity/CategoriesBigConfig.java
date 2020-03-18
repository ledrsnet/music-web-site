package com.maple.music.entity;

import javax.persistence.*;

/**
 * @author LiangDong
 * @Date 2020/3/2
 */
@Entity
@Table(name = "m_categories_big_config", schema = "ssh_music")
public class CategoriesBigConfig {
	private Integer id;
	private Integer code;
	private String codeText;
	@Id
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Basic
	@Column(name = "code")
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Basic
	@Column(name = "code_text")
	public String getCodeText() {
		return codeText;
	}

	public void setCodeText(String codeText) {
		this.codeText = codeText;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CategoriesBigConfig that = (CategoriesBigConfig) o;

		if (code != null ? !code.equals(that.code) : that.code != null) return false;
		if (codeText != null ? !codeText.equals(that.codeText) : that.codeText != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = code != null ? code.hashCode() : 0;
		result = 31 * result + (codeText != null ? codeText.hashCode() : 0);
		return result;
	}
}
