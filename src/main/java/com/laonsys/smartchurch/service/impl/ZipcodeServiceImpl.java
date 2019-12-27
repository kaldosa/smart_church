package com.laonsys.smartchurch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laonsys.smartchurch.domain.Zipcode;
import com.laonsys.smartchurch.mapper.ZipcodeMapper;
import com.laonsys.smartchurch.service.ZipcodeService;

@Service("zipcodeService")
public class ZipcodeServiceImpl implements ZipcodeService {
	
	@Autowired
	private ZipcodeMapper zipcodeMapper;
	
	@Override
	public List<Zipcode> serachByDong(String keyAddr) {
		return zipcodeMapper.searchByDong(keyAddr + "%");
	}
}
