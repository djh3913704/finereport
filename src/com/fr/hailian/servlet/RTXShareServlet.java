package com.fr.hailian.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.fr.fs.base.entity.User;
import com.fr.hailian.core.BaseServlet;
import com.fr.hailian.util.DESSymmetricEncoder;
import com.fr.hailian.util.PortalService;
import com.fr.hailian.util.RoleUtil;
/**
 * 
 * @className RTXShareServlet.java
 * @time   2017年8月10日 下午3:24:44
 * @author zuoqb
 * @todo   RTX集成 多级上报提交分享后续处理
 */
public class RTXShareServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8091436245999618392L;

	/**
	 * Constructor of the object.
	 */
	public RTXShareServlet() {
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
		overwriteReport(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		overwriteReport(request,response);
	}

	private void overwriteReport(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject r=new JSONObject();
		System.out.println("RTX集成 多级上报提交分享后续处理开始...... ");
		try {
			//用户名
			User user =RoleUtil.getCurrentUser(request);
			String domain=request.getParameter("domain");
			System.out.println("userName:"+user);
			/*String name = java.net.URLDecoder.decode(request.getParameter(Constants.FR_USERNAME),"UTF-8");
			System.out.println("name:"+name);*/
			if(user!=null){
				//获取下一级审核人信息
				String toUserIds="test001";
				
				//生成url地址 发送RTX信息使用
				String sign=DESSymmetricEncoder.createSign(user.getId()+"");
				String url=domain+"/rtxSecurityServlet?sign="+sign+"&userId="+user.getId();
				System.out.println(url);
				if(PortalService.sendMessageToUser(request,"多级上报未处理信息提醒", "BI平台", url, toUserIds)){
					r.put("fail", false);
					r.put("msg", "发送RTX信息成功!");
				}else{
					r.put("fail", true);
					r.put("msg", "发送RTX信息失败!");
				};
			}else{
				//先登录
				//response.sendRedirect("/WebReport/ReportServer?op=fs");
				r.put("fail", true);
				r.put("msg", "请先登录!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		responseOutWithJson(response, r);
	}

	public void init() throws ServletException {
		//System.out.println("RTX集成 多级上报提交分享后续处理初始化...... ");
	}

}
