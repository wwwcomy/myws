package com.iteye.wwwcomy.bean;

/**
 * Http请求
 * 
 * @author wwwcomy
 * 
 */
public class Request {

	private RequestLine reqLine;

	private MimeHeader header;
	
	private String wholeHead;

	/**
	 * TODO 暂时未处理
	 */
	private String body;

	public Request() {
	}

	public Request(String reqHeader) {
		String[] arr = reqHeader.split("\r\n");
		this.setReqLine(new RequestLine(arr[0]));
		this.header = new MimeHeader(arr);
	}

	public Request parseReq(String reqHeader) {
		Request req = new Request();
		return req;
	}

	public MimeHeader getHeader() {
		return header;
	}

	public void setHeader(MimeHeader header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public RequestLine getReqLine() {
		return reqLine;
	}

	public void setReqLine(RequestLine reqLine) {
		this.reqLine = reqLine;
	}

	public Cookie[] getCookies() {
		return this.getHeader().getCookies();
	}

	@Override
	public String toString() {
		String reqLine = this.reqLine.toString();
		String header = this.header.toString();
		return "reqLine:\n" + reqLine + "\nheader" + header;
	}

	public String getWholeHead() {
		return wholeHead;
	}

	public void setWholeHead(String wholeHead) {
		this.wholeHead = wholeHead;
	}
}
