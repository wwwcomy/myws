package com.iteye.wwwcomy;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.iteye.wwwcomy.util.DebugUtil;
import com.iteye.wwwcomy.util.ThreadPoolUtil;

public class EndPoint {

	public void start() {
		try {
			ServerSocket ss = new ServerSocket(5000);
			DebugUtil.info("Server Started!");
			while (true) {
				Socket s = ss.accept();
				DebugUtil.info("Got a request...");
				processSocket(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void processSocket(Socket s) {
		ThreadPoolUtil.execute(new Worker(s));
	}
}

class Worker implements Runnable {

	Socket s;

	public Worker(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		try {
			// DataInputStream dis = new DataInputStream(s.getInputStream());
			// byte[] b = new byte[1024];
			// StringBuilder sb = new StringBuilder();
			// s.setSoTimeout(10);
			// int len = -1;
			// while ((len = dis.read(b)) != -1) {
			// sb.append(new String(b));
			// // for (byte bByte : b)
			// // System.out.println(bByte);
			// }

			InputStreamReader dis = new InputStreamReader(s.getInputStream());
			char[] bs = new char[2048];
			StringBuilder sb = new StringBuilder();
			// 如果10毫秒还没有数据，则视同没有新的数据了。
			// 因为有Keep-Alive的缘故，浏览器可能不主动断开连接的。
			// 实际应用，会根据协议第一行是GET还是 POST确定。
			s.setSoTimeout(5000);
			int len = -1;
			try {
				while ((len = dis.read(bs)) != -1) {
					System.out.println(1);
					sb.append(bs, 0, len);
					sb.append("\n");
				}
			} catch (Throwable e) {
				System.out.println(2);
			}

			PrintStream out = new PrintStream(s.getOutputStream());
			System.out.println("Date Info. has been sent back.");
			out.println("HTTP/1.1 200 OK");
			out.println("Content-Type:text/html;charset:UTF-8");
			out.println();
			out.println("<HTML><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" /></head><BODY>"
					+ "<center>"
					+ "<H1>HTTP协议测试服务器,当前时间："
					+ new java.util.Date()
					+ "</h1>"
					+ "<form method='get'>username:<input type='text' name='username'/>password:<input type='text' name='password'/><input type='submit' value='GET测试'/></form><br/>"
					+ "<form method='post'>username:<input type='text' name='username'/>password:<input type='text' name='password'/><input type='submit' value='POST测试'/></form><br/>"
					+ "<form method='post'  enctype='multipart/form-data'>phototitle:<input type='text' name='phototitle'/>photo:<input type='file' name='photo'/><input type='submit' value='Upload测试'/></form>"
					+ "</center>您提交的数据如下:<pre>" + sb.toString() + "</pre></BODY></HTML>");
			out.flush();
			out.close();
			dis.close();
			s.close();
			DebugUtil.info("closed");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}
}
