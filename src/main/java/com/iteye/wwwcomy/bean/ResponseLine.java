package com.iteye.wwwcomy.bean;

/**
 * 请求起始行
 * 
 * @author wwwcomy
 * 
 */
public class ResponseLine {
	private String version = "HTTP/1.1";
	private int status = 200;
	private String reasonPhrase = "OK";

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public void setReasonPhrase(String reasonPhrase) {
		this.reasonPhrase = reasonPhrase;
	}

	public ResponseLine() {
	}

	@Override
	public String toString() {
		return "Response version:" + this.version + "\nRequest status:" + this.status + "\nreasonPhrase:"
				+ this.reasonPhrase;
	}

	public boolean destroy() {
		this.version = null;
		this.status = 0;
		this.reasonPhrase = null;
		return true;
	}
}
