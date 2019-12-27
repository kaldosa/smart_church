package com.laonsys.smartchurch.domain;

import lombok.Data;
import lombok.ToString;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

@ToString(includeFieldNames = true)
public @Data class Authority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4516500106591371077L;

	private long userId;

	private String role;

	public Authority() {
	}

	public Authority(String role) {
		Assert.hasText(role, "A granted authority textual representation is required");
		this.role = role;
	}

	public Authority(long id, String role) {
	    Assert.hasText(role, "A granted authority textual representation is required");
	    this.userId = id;
	    this.role = role;
	}

	@Override
	public String getAuthority() {
		return role;
	}
}
