package com.fr.hailian.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fr.fs.base.entity.User;
import com.fr.fs.control.UserControl;
import com.fr.hailian.util.BaseServlet;
import com.fr.hailian.util.Constants;
import com.fr.hailian.util.PortalService;
import com.fr.hailian.util.RoleUtil;
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
		//根据token获取用户信息
		Map<String,Object> result = PortalService.getUserInfoByToken(token, Constants.PORTAL_FROM);
		String name = (String) result.get("name");//获取用户名，需进一步确认
		//String name = "tom";
		try {
			User user = UserControl.getInstance().getByUserName(name);//获取用户对象
			if(RoleUtil.judgeAuxiliaryRole(user)){
				//生成登陆凭证
				RoleUtil.loginCMD(hrequest, response);
				r.put("fail", false);
				r.put("msg", "单点登录成功");
			}else{
				r.put("fail", true);
				r.put("msg", "该用户没有辅助决策系统权限，请联系管理员!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseOutWithJson(response, r);
	}

	public void init() throws ServletException {
		// Put your code here
		System.out.println("单点登录逻辑初始化...... ");
	}

}
