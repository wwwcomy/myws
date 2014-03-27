package com.iteye.wwwcomy.bean;

import java.io.OutputStream;
import java.io.PrintStream;

import com.iteye.wwwcomy.util.DebugUtil;

public class Response {

	private OutputStream outputStream;

	ResponseLine respLine = new ResponseLine();

	private String body;

	public Response(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public int getStatus() {
		return respLine.getStatus();
	}

	public void setStatus(int status) {
		respLine.setStatus(status);
	}

	public String getVersion() {
		return respLine.getVersion();
	}

	public void setVersion(String version) {
		respLine.setVersion(version);
	}

	public String getReasonPhrase() {
		return respLine.getReasonPhrase();
	}

	public void setReasonPhrase(String reasonPhrase) {
		respLine.setReasonPhrase(reasonPhrase);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void flush() {

		PrintStream out = new PrintStream(this.getOutputStream());
		StringBuilder sb = new StringBuilder();
		sb.append(getVersion()).append(" ").append(getStatus()).append(" ").append(getReasonPhrase()).append("\n");
		if (getStatus() != 404) {
			sb.append("Content-Type:text/html;charset:UTF-8\n");
			sb.append("\n");
			sb.append(body);
		}
		out.print(sb.toString());
		DebugUtil.info("Response send back!");
		out.flush();
		out.close();
	}

}
