package com.fr.hailian.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fr.fs.base.entity.User;
import com.fr.fs.control.UserControl;
import com.fr.fs.web.service.AbstractFSAuthService;
import com.fr.hailian.util.BaseServlet;
import com.fr.hailian.util.PortalService;
import com.fr.hailian.util.RoleUtil;
/**
 * 
 * @time   2017年8月10日 下午3:24:44
 * @author zuoqb
 * @todo   注销
 */
public class LogoutServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8091436245999618392L;

	/**
	 * Constructor of the object.
	 */
	public LogoutServlet() {
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
		overwriteLogout(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		overwriteLogout(request,response);
	}

	private void overwriteLogout(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject r=new JSONObject();
		try {
			User user =RoleUtil.getCurrentUser(request);
			if(user!=null){
				//step1 本地注销
				UserControl.getInstance().logout(user.getId());
				//认证服务系统注销
				if(PortalService.logout(user.getUsername())){
					//去首页
					//response.sendRedirect("/WebReport/ReportServer?op=fs");
					r.put("fail", false);
					r.put("msg", "/WebReport/ReportServer?op=fs");
				}else{
					r.put("fail", true);
					r.put("msg", "注销失败！");
				}
			}else{
				r.put("fail", true);
				r.put("msg", "该用户不存在，请注册！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseOutWithJson(response, r);
	}

	public void init() throws ServletException {
	}

}
