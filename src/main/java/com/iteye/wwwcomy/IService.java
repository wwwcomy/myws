package com.iteye.wwwcomy;

import com.iteye.wwwcomy.bean.Request;
import com.iteye.wwwcomy.bean.Response;

public interface IService {
	public void service(Request req, Response resp) throws Throwable;
}
