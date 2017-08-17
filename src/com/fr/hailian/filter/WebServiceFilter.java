package com.fr.hailian.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.xml.ws.Endpoint;

import com.fr.hailian.core.BaseServlet;
import com.fr.hailian.core.Constants;
import com.fr.hailian.wsdl.TaskWebService;
/**
 * 
 * @className FRFilter.java
 * @time   2017年8月7日 下午2:15:22
 * @author zuoqb
 * @todo   对外发布WebService（wsdl）拦截器
 */
public class WebServiceFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain Chain) throws IOException, ServletException {
	}

	public void init(FilterConfig arg0) throws ServletException {
		String domain="http://"+BaseServlet.getIpAddress()+":"+Constants.WebService_Port+"/Service";
		//统一待办已办接口
		Endpoint.publish(domain+"/TaskWebService",new TaskWebService());

	}

}
