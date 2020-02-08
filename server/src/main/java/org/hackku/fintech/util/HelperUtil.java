package org.hackku.fintech.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class HelperUtil {
	
	public static String getParameterUrlEncoding(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder url = new StringBuilder("?");
		for(Map.Entry<String, String> entry : params.entrySet()) {
			url = url.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), "UTF-8")).append("&");
		}
		return url.toString();
	}

}
