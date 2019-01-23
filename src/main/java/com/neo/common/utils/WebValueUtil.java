package com.neo.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class WebValueUtil {

	public static String getWebValue(HttpServletRequest request,String key) {
		String value = request.getParameter(key);
		if(value==null) {
			value = "";
		}
		return value.replaceAll("\\s*", "");
	}
	
	public static String getWebDefaultValue(HttpServletRequest request, String value, String defaultValue) {
		String val = request.getParameter(value);
		if (StringUtils.isBlank(val)) {
			val = defaultValue;
		}
		return val.replaceAll("\\s*", "");
	}

	
	public static int getWebPageNo(HttpServletRequest request, String value, int i) {
		String pageIndex = request.getParameter(value);
		if (StringUtils.isNotBlank(pageIndex)) {
			return Integer.parseInt(pageIndex);
		}
		return i;
	}
	
	
	public static boolean isNotEmptyBatch(String... strs) {
		for (String str : strs) {
			if (StringUtils.isBlank(str)) {
				continue;
			} else {
				return true;
			}
		}
		return false;
	}
}
