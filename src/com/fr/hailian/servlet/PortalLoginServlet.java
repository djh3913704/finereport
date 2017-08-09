package com.fr.hailian.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fr.hailian.util.BaseServlet;
/**
 * 
 * @className PortalLoginServlet.java
 * @time   2017年8月8日 下午6:03:52
 * @author zuoqb
 * @todo   单点登录逻辑
 */
public class PortalLoginServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8091436245999618392L;

	/**
	 * Constructor of the object.
	 */
	public PortalLoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		overwriteLogin(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		overwriteLogin(request,response);
	}

	private void overwriteLogin(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		JSONObject r=new JSONObject();
		System.out.println("单点登录逻辑开始...... ");
		HttpServletRequest hrequest = (HttpServletRequest)request;//web资源
		String token=hrequest.getParameter("token");
		String redictUrl=hrequest.getParameter("redictUrl");
		
		r.put("msg", "");
		responseOutWithJson(response, r);
	}

	public void init() throws ServletException {
		// Put your code here
		System.out.println("单点登录逻辑初始化...... ");
	}

}
