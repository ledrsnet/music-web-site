package com.maple.music.entity;

/**
 * @author LiangDong
 * @Date 2020/3/30
 */
public class TokenDto {
	private String token;
	private Integer start_offset;
	private Integer end_offset;
	private String type;
	private Integer position;

	public TokenDto() {
	}

	public TokenDto(String token, Integer start_offset, Integer end_offset, String type, Integer position) {
		this.token = token;
		this.start_offset = start_offset;
		this.end_offset = end_offset;
		this.type = type;
		this.position = position;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getStart_offset() {
		return start_offset;
	}

	public void setStart_offset(Integer start_offset) {
		this.start_offset = start_offset;
	}

	public Integer getEnd_offset() {
		return end_offset;
	}

	public void setEnd_offset(Integer end_offset) {
		this.end_offset = end_offset;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return "TokenDto{" +
				"token='" + token + '\'' +
				", start_offset=" + start_offset +
				", end_offset=" + end_offset +
				", type='" + type + '\'' +
				", position=" + position +
				'}';
	}
}
