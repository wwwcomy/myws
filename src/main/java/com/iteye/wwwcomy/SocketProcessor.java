package com.iteye.wwwcomy;

import java.net.Socket;

import com.iteye.wwwcomy.bean.Request;
import com.iteye.wwwcomy.bean.Response;

public class SocketProcessor {

	Socket s;

	IService service;

	public IService getService() {
		return service;
	}

	public void setService(IService service) {
		this.service = service;
	}

	public SocketProcessor(Socket s) {
		this.s = s;
		this.service = new DefaultService();
	}

	public void process() throws Throwable {
		CustomDataInputStream input = new CustomDataInputStream(s.getInputStream());
		String content = null;
		StringBuilder sb = new StringBuilder();
		content = input.readHttpRequestHeaderLine();
		sb.append(content);
		String reqHeader = sb.toString();

		Request req = new Request(reqHeader);
		req.setWholeHead(reqHeader);
		Response resp = new Response(s.getOutputStream());

		service.service(req, resp);

		input.close();
	}
}
