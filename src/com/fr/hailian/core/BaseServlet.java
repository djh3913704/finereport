package com.fr.hailian.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class BaseServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8095999134950661589L;

	protected void responseOutWithJson(HttpServletResponse response,
			JSONObject responseObject) {
		// 将实体对象转换为JSON Object转换
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(responseObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	public static String getIpAddress() {
		try {
			InetAddress address = InetAddress.getLocalHost();// 获取的是本地的IP地址
			String hostAddress = address.getHostAddress();// 192.168.0.121
			return hostAddress;
		} catch (Exception e) {
		}
		return null;
	}
}
