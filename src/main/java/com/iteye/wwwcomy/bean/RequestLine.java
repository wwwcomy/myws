package com.iteye.wwwcomy.bean;

/**
 * 请求起始行
 * 
 * @author wwwcomy
 * 
 */
public class RequestLine {
	private String method;
	private String URL;
	private String version;

	public RequestLine() {
	}

	public RequestLine(String reqLine) {
		try {
			String arr[] = reqLine.split(" ");
			this.method = arr[0];
			this.URL = arr[1];
			this.version = arr[2];
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Request Method:" + this.method + "\nRequest URL:" + this.URL + "\nVersion:" + this.version;
	}

	public boolean destroy() {
		this.method = null;
		this.URL = null;
		this.version = null;
		return true;
	}
}
