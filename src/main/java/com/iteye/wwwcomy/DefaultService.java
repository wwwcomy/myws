package com.iteye.wwwcomy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.iteye.wwwcomy.bean.Request;
import com.iteye.wwwcomy.bean.Response;
import com.iteye.wwwcomy.util.StringUtil;

public class DefaultService implements IService {

	public static String WEBAPP_PATH = "D:/cloudrepository/myws/trunk/webapp";
	public static String DEFAULT_URL = "index.jsp";

	@Override
	public void service(Request req, Response resp) throws Throwable {
		String url = req.getReqLine().getURL();

		if (url.equals("/")) {
			url = url + DEFAULT_URL;
		}

		File file = new File(new File(WEBAPP_PATH), url);
		if (file.exists()) {
			resp.setStatus(200);
			String content = getContent(file);
			resp.setBody(content);
		} else {
			resp.setStatus(404);
			resp.setReasonPhrase("Not Found");
		}
		resp.flush();
	}

	private String getContent(File file) throws IOException {
		BufferedReader fr = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		String tmp;
		try {
			while (!StringUtil.isBlankOrNull((tmp = fr.readLine()))) {
				sb.append(tmp);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			fr.close();
		}
		return sb.toString();
	}

}
