package com.iteye.wwwcomy.bean;

import java.util.HashMap;

import com.iteye.wwwcomy.util.StringUtil;

/**
 * Header块
 * 
 * @author wwwcomy
 * 
 */
public class MimeHeader {

	private HashMap<String, String> map = new HashMap<String, String>();

	private Cookie[] cookies;

	public Cookie[] getCookies() {
		return cookies;
	}

	public MimeHeader(String[] arr) {
		int len = arr.length;
		for (int i = 1; i < len; i++) {
			String tmpLine = arr[i];
			if (!StringUtil.isBlankOrNull(tmpLine)) {
				String[] KV = tmpLine.split(":");
				String key = KV[0].trim();
				if (key.equalsIgnoreCase("Cookie")) {
					parseCookie(KV[1].trim());
				} else
					map.put(key, KV[1].trim());
			}
		}
	}

	private void parseCookie(String values) {
		String[] arr = values.split(";");
		int len = arr.length;
		cookies = new Cookie[len];
		for (int i = 0; i < len; i++) {
			String[] KV = arr[i].trim().split("=");
			cookies[i] = new Cookie(KV[0].trim(), KV[1].trim());
		}

	}

	public void set(String key, String value) {
		map.put(key, value);
	}

	public String get(String key) {
		return map.get(key);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Cookie c : cookies) {
			sb.append(c.toString()).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("\n");
		return this.map.toString() + "\n" + sb.toString();
	}

	public boolean destroy() {
		this.map.clear();
		this.map = null;
		this.cookies = null;
		return true;
	}
}
