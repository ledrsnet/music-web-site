package com.maple.music.entity;

import java.util.List;

/**
 * @author LiangDong
 * @Date 2020/3/30
 */
public class TokensDto {
	private List<TokenDto> tokens;

	public TokensDto() {
	}

	public TokensDto(List<TokenDto> tokens) {
		this.tokens = tokens;
	}

	public List<TokenDto> getTokens() {
		return tokens;
	}

	public void setTokens(List<TokenDto> tokens) {
		this.tokens = tokens;
	}

	@Override
	public String toString() {
		return "TokensDto{" +
				"tokens=" + tokens +
				'}';
	}
}
