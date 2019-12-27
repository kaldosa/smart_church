package com.laonsys.smartchurch.utiles;

import org.apache.commons.beanutils.BeanUtilsBean;

public class CommonUtils {
	
	@SuppressWarnings("unchecked")
	public static <T> T cloneBean(T obj) {
		try {
			return (T) BeanUtilsBean.getInstance().cloneBean(obj);
		} catch(Exception e) {
			throw new RuntimeException("Not support clone.", e);
		}
	}
}
