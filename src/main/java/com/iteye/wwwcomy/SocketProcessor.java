package com.iteye.wwwcomy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import com.iteye.wwwcomy.util.DebugUtil;

public class SocketProcessor {

	Socket s;

	public SocketProcessor(Socket s) {
		this.s = s;
	}

	public void process() {
		try {
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

	public void test(Socket socket) throws Throwable{
		// 准备接受HTTP响应头并解析
		CustomDataInputStream input = new CustomDataInputStream(socket.getInputStream());
		File myFile = new File("d:/1.txt");
		String content = null;
		HttpResponseHeaderParser responseHeader = new HttpResponseHeaderParser();
		BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(myFile));
		boolean hasData = false;
		while((content = input.readHttpResponseHeaderLine()) != null) {
			System.out.println("response header contect -->> " + content);
			responseHeader.addResponseHeaderLine(content);
			if(content.length() == 0) {
				hasData = true;
			}
			if(hasData) {
				int totalBytes = responseHeader.getFileLength();
				if(totalBytes == 0) break; // no response body and data
				int offset = 0;
				byte[] myData = null;
				if(totalBytes >= 2048) {
					myData = new byte[2048];
				} else {
					myData = new byte[totalBytes];
				}
				int numOfBytes = 0;
				while((numOfBytes = input.read(myData, 0, myData.length)) > 0 && offset < totalBytes) {
					offset += numOfBytes;
					// float p = ((float)offset) / ((float)totalBytes) * 100.0f;
					if(offset > totalBytes) {
						numOfBytes = numOfBytes + totalBytes - offset;
						// p = 100.0f;
					}
					output.write(myData, 0, numOfBytes);
					// updateStatus(p);
				}
				hasData = false;
				break;
			}
		}
	}
}
