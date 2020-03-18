package com.maple.music.entity;

import javax.persistence.*;

/**
 * @author LiangDong
 * @Date 2020/3/2
 */
@Entity
@Table(name = "m_categories_config", schema = "ssh_music")
public class CategoriesConfig {
	private Integer id;
	private String name;
	private Integer category;
	private Integer hot;

	@Id
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	@Column(name = "category")
	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	@Basic
	@Column(name = "hot")
	public Integer getHot() {
		return hot;
	}

	public void setHot(Integer hot) {
		this.hot = hot;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CategoriesConfig that = (CategoriesConfig) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (category != null ? !category.equals(that.category) : that.category != null) return false;
		if (hot != null ? !hot.equals(that.hot) : that.hot != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (category != null ? category.hashCode() : 0);
		result = 31 * result + (hot != null ? hot.hashCode() : 0);
		return result;
	}
}
