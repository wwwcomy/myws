package com.iteye.wwwcomy;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CustomDataInputStream extends DataInputStream {
	public CustomDataInputStream(InputStream in) {
		super(in);
	}

	public String readHttpResponseHeaderLine() throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		while (true) {
			int b = read();
			if (b < 0) {
				throw new IOException("Data truncated");
			}
			if (b == 10) {
				break;
			}
			if (b == 13) {
				continue;
			}
			buffer.write(b);
		}
		return new String(buffer.toByteArray(), "UTF-8");
	}

	public String readHttpRequestHeaderLine() throws IOException {
		byte[] buf = new byte[8192];
		boolean stop = false;
		int iRead = 0;
		int lastValid = 0;
		while (!stop) {
			iRead = read(buf, 0, buf.length - lastValid);
			if (iRead > 0)
				lastValid = iRead;
			stop = true;
		}
		return new String(buf, 0, lastValid, "UTF-8");
	}
}
