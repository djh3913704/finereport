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

import com.fr.fs.base.entity.User;
import com.fr.fs.base.entity.UserInfo;
import com.fr.fs.control.UserControl;
import com.fr.fs.privilege.auth.FSAuthentication;
import com.fr.fs.privilege.base.FServicePrivilegeLoader;
import com.fr.fs.privilege.entity.DaoFSAuthentication;
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

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain Chain) throws IOException, ServletException {
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("拦截器例子初始化.....");
	}

}
