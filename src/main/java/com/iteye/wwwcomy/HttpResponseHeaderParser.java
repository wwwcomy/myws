package com.iteye.wwwcomy;

import java.util.HashMap;
import java.util.Map;

/**
 * it can parse entity header, response head
 * and response line <status code, CharSet, ect...>
 * refer to RFC2616，关于HTTP响应头，请看RFC文档，描写的很详细啊！！
 * 
 * @author fish
 *
 */
public class HttpResponseHeaderParser {
	public final static String CONTENT_LENGTH = "Content-Length";
	public final static String CONTENT_TYPE = "Content-Type";
	public final static String ACCEPT_RANGES = "Accetp-Ranges";
	
	private Map<String, String> headerMap;
	public HttpResponseHeaderParser() {
		headerMap = new HashMap<String, String>();
	}
	/**
	 * <p> get the response header key value pair </p>
	 * @param responseHeaderLine
	 */
	public void addResponseHeaderLine(String responseHeaderLine) {
		if(responseHeaderLine.contains(":")) {
			String[] keyValue = responseHeaderLine.split(": ");
			if(keyValue[0].equalsIgnoreCase(CONTENT_LENGTH)) {
				headerMap.put(CONTENT_LENGTH, keyValue[1]);
			} else if(keyValue[0].equalsIgnoreCase(CONTENT_TYPE)) {
				headerMap.put(CONTENT_TYPE, keyValue[1]);
			} else {
				headerMap.put(keyValue[0], keyValue[1]);
			}
		}
	}
	
	public int getFileLength() {
		if(headerMap.get(CONTENT_LENGTH) == null){
			return 0;
		}
		return Integer.parseInt(headerMap.get(CONTENT_LENGTH));
	}
	
	public String getFileType() {
		return headerMap.get(CONTENT_TYPE);
	}
	public Map<String, String> getAllHeaders() {
		return headerMap;
	}

}
