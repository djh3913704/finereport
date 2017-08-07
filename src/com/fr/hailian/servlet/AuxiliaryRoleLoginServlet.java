package com.fr.hailian.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.fs.base.entity.CustomRole;
import com.fr.fs.base.entity.User;
import com.fr.fs.control.UserControl;
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
		String result="";
		System.out.println("辅助决策登陆改造开始...... ");
		HttpServletRequest hrequest = (HttpServletRequest)request;//web资源
		String name=hrequest.getParameter("name");
		String password=hrequest.getParameter("password");
		System.out.println("name:"+name+",password:"+password);
		try {
			User user = UserControl.getInstance().getUser(name,password);//获取用户对象
			System.out.println("user:"+user);
			if(user!=null){
				//判断是否是超级管理员
				long superManagerID=UserControl.getInstance().getSuperManagerID();//超级管理员ID
				boolean isAdmin = superManagerID == user.getId(); //判断是否是管理员
				if(isAdmin){
					/**
					 * 是超级管理员
					 * step1:用户名 密码校验 这个在上面已经验证了
					 * step2：生成登陆凭证
					 */
					
				}else{
					/**
					 * 不是超级管理员
					 * step1:统一身份认证userValidate 
					 * step2:辅助决策系统权限认证
					 * step3:生成登陆凭证
					 */
					//step1:统一身份认证userValidate 
					
					//step2:辅助决策系统权限认证
					Set<CustomRole> roles=UserControl.getInstance().getSRoles(user.getId());//根据用户id获取该所属的所有角色
					Iterator<CustomRole> it = roles.iterator();  
					boolean hasRole=false;
					while (it.hasNext()) {  
						CustomRole role = it.next();  
						System.out.println(role.getId());  
						if(role.getId()==com.fr.hailian.util.Constants.AUXILIARYROLE_ID){
							hasRole=true;
							break;
						}
					};
					if(hasRole){
						//step3:生成登陆凭证
						
					}else{
						result="该用户没有辅助决策系统权限，请联系管理员!";
					}
				}
			}else{
				result="用户名或者密码错误!返回登陆页";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", 认证结果:"+result);
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
		
		
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
