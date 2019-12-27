package com.laonsys.springmvc.extensions.utils;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class EncodingUtils {
	public static String encodePassword(String rawPass, Object salt) {
		return new ShaPasswordEncoder(256).encodePassword(rawPass, salt);
	}
}
