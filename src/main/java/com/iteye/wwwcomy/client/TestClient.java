package com.iteye.wwwcomy.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

public class TestClient {
	public static void main(String[] args) throws IOException {
		URL url = new URL("localhost:5000");
		String host = url.getHost();
		int port = (url.getPort() == -1) ? url.getDefaultPort() : url.getPort();
		System.out.println("Host Name = " + host);
		System.out.println("port = " + port);
		System.out.println("File URI = " + url.getFile());

		// create socket and start to construct the request line
		Socket socket = new Socket();
		SocketAddress address = new InetSocketAddress(host, port);
		socket.connect(address);
	}

	public static void genHttp() throws Throwable {
		Socket socket = null;
		String url = null;
		String host = null;
		BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF8"));
		String requestStr = "GET " + url + " HTTP/1.1\r\n"; // request
															// line

		// construct the request header - 构造HTTP请求头(request header)
		String hostHeader = "Host: " + host + "\r\n";
		String acceptHeader = "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n";
		String charsetHeader = "Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n";
		String languageHeader = "Accept-Language: zh-CN,zh;q=0.8\r\n";
		String keepHeader = "Connection: close\r\n";
		
		bufferedWriter.write(requestStr);  
		bufferedWriter.write(hostHeader);  
		bufferedWriter.write(acceptHeader);  
		bufferedWriter.write(charsetHeader);  
		bufferedWriter.write(languageHeader);  
		bufferedWriter.write(keepHeader);  
		bufferedWriter.write("\r\n"); // 请求头信息发送结束标志  
		bufferedWriter.flush();  
	}
}
