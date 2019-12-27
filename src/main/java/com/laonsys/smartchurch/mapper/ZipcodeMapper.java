package com.laonsys.smartchurch.mapper;

import java.util.List;

import com.laonsys.smartchurch.domain.Zipcode;

public interface ZipcodeMapper {
	public List<Zipcode> searchByDong(String dong);
}
