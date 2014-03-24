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
}
