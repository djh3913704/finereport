package com.fr.hailian.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fr.fs.base.entity.User;
import com.fr.fs.base.entity.UserInfo;
import com.fr.fs.control.UserControl;
import com.fr.fs.privilege.auth.FSAuthentication;
import com.fr.fs.privilege.base.FServicePrivilegeLoader;
import com.fr.fs.privilege.entity.DaoFSAuthentication;
import com.fr.fs.web.service.ServiceUtils;
import com.fr.privilege.PrivilegeManager;
import com.fr.privilege.session.PrivilegeInfoSessionMananger;
/**
 * 
 * @className LoginServlet.java
 * @time   2017年8月7日 下午2:17:11
 * @author zuoqb
 * @todo   辅助决策登陆改造
 */
public class AuxiliaryRoleLoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8091436245999618392L;

	/**
	 * Constructor of the object.
	 */
	public AuxiliaryRoleLoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("辅助决策登陆改造开始...... ");
		System.out.println("start");
		System.out.println("end-----");
		HttpServletRequest hrequest = (HttpServletRequest)request;//web资源
        HttpServletResponse response2 = (HttpServletResponse) response;
        HttpSession session = hrequest.getSession();
        String username = hrequest.getParameter("name");
        System.out.println("用户名是："+username);
        
        long userID = ServiceUtils.getCurrentUserID(hrequest);//req为HttpServletRequest类型,要拿到userid必须保证登录(或者但单点登陆)平台
		boolean isAdmin = userID == PrivilegeManager.SYSADMINID; //判断是否是管理员
		System.out.println("userID："+userID);
		System.out.println("isAdmin："+isAdmin);
       
        try {
			User user = UserControl.getInstance().getUser(userID);
			System.out.println("user："+user);
			if (user !=null) {
				FSAuthentication authentication = new DaoFSAuthentication(new UserInfo(user.getId(), user.getUsername(), user.getPassword()));
				long userid = authentication.getUserInfo().getId();
				PrivilegeInfoSessionMananger.login(new FServicePrivilegeLoader(user.getUsername(), UserControl.getInstance().getAllSRoleNames(userid), UserControl.getInstance().getUserDP(userid)), session, response2);
                //session
				session.setAttribute("fr_fs_auth_key", authentication);
                //用户注册
                UserControl.getInstance().login(userid);
			}
			//跳转到fs
			response2.sendRedirect("/WebReport/ReportServer?op=fs");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("zuo init Login HttpServlet doPost ");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	public void init() throws ServletException {
		// Put your code here
		System.out.println("辅助决策登陆改造初始化...... ");
	}

}
