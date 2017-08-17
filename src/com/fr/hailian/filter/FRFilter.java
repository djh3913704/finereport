package com.fr.hailian.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Endpoint;

import com.fr.fs.base.entity.User;
import com.fr.fs.base.entity.UserInfo;
import com.fr.fs.control.UserControl;
import com.fr.fs.privilege.auth.FSAuthentication;
import com.fr.fs.privilege.base.FServicePrivilegeLoader;
import com.fr.fs.privilege.entity.DaoFSAuthentication;
import com.fr.hailian.wsdl.TaskWebService;
import com.fr.privilege.session.PrivilegeInfoSessionMananger;
/**
 * 
 * @className FRFilter.java
 * @time   2017年8月7日 下午2:15:22
 * @author zuoqb
 * @todo   拦截器例子
 */
public class FRFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("d");

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain Chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("start......");
		System.out.println("end-----");
		HttpServletRequest hrequest = (HttpServletRequest)request;//web资源
        HttpServletResponse response2 = (HttpServletResponse) response;
        HttpSession session = hrequest.getSession();
        String username = hrequest.getParameter("name");
        
        System.out.println("用户名是："+username);
        
        
        try {
			User user = UserControl.getInstance().getByUserName(username);
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

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("拦截器例子初始化.....");

	}

}
