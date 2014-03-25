package com.iteye.wwwcomy;

import java.io.IOException;
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
			new SocketProcessor(s).process();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
