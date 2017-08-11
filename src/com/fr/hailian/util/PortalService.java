package com.fr.hailian.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;

import org.json.JSONObject;

import com.fr.fs.base.entity.User;
import com.fr.fs.web.FSConstants;
import com.fr.hailian.webservice.SSOService;
import com.fr.hailian.webservice.SSOServiceSoap;
import com.fr.stable.StringUtils;

public class PortalService {
	
	private static final QName SERVICE_NAME = new QName("http://sso.nwh.cn", "SSOService");
	
	/***
	 * 校验用户
	 * @param account 用户名
	 * @param password 密码
	 * @param form sysid
	 * @return
	 */
	public static Map<String,Object> userValidate(String account,String password,String form){
		//拼接字符串
		String info = "{\"Account\":\""+ account +"\",\"Password\":\""+ password +"\",\"From\":\""+ form +"\"}";
	    SSOService ss = new SSOService(SSOService.WSDL_LOCATION, SERVICE_NAME);
	    SSOServiceSoap port = ss.getSSOServiceSoap();  
        java.lang.String _userValidate__return = port.userValidate(info);
        if(StringUtils.isNotBlank(_userValidate__return)){
        	return JsonKit.json2map(_userValidate__return);
        }
		return new HashMap<String, Object>();
		
	}
	/***
	 * 校验用户
	 * @param account 用户名
	 * @param password 密码
	 * @param form sysid
	 * @return
	 */
	public static Map<String,Object> userValidate(String account,String password){
		return  userValidate(account, password, Constants.PORTAL_FROM);
		
	}
	/***
	 * 
	 * @param account 用户名
	 * @param password userValidate返回的token口令
	 * @param target 
	 * @return
	 */
	public static Map<String,Object> getTargetEntry(String account,String password,String target){
		//拼接字符串
		String info = "{\"Account\":\""+ account +"\",\"Password\":\""+ password +"\",\"Target\":\""+ target +"\"}";
	    SSOService ss = new SSOService(SSOService.WSDL_LOCATION, SERVICE_NAME);
	    SSOServiceSoap port = ss.getSSOServiceSoap();
	    java.lang.String _getTargetEntry__return = port.getTargetEntry(info);
        if(StringUtils.isNotBlank(_getTargetEntry__return)){
        	return JsonKit.json2map(_getTargetEntry__return);
        }
		return new HashMap<String, Object>();
		
	}
	
	/***
	 * 
	 * @param token userValidate返回的token口令
	 * @param sysID
	 * @return
	 */
	public static Map<String,Object> getUserInfoByToken(String token, String target){
		String info = "{\"Token\":\""+ token +"\",\"Target\":\""+ target +"\"}";//格式需要在确认下
	    SSOService ss = new SSOService(SSOService.WSDL_LOCATION, SERVICE_NAME);
	    SSOServiceSoap port = ss.getSSOServiceSoap();
	    java.lang.String userInfo=port.getUserInfoByToken(info);
	    if(StringUtils.isNotBlank(userInfo)){
        	return JsonKit.json2map(userInfo);
        }
		return new HashMap<String, Object>();
		
	}
	
	/***
	 * 修改密码
	 * @param loginName 用户名
	 * @param oldPasswd 原密码
	 * @param newPasswd 新密码
	 * @return “-1”：账号不正确；“-2”：原密码错误 “0”：修改失败；“1”：修改成功
	 */
	public static Map<String,Object> changePassword(String loginName,String oldPasswd,String newPasswd){
	    SSOService ss = new SSOService(SSOService.WSDL_LOCATION, SERVICE_NAME);
	    SSOServiceSoap port = ss.getSSOServiceSoap();  
        java.lang.String _changePassword__return = port.changePassword(loginName, oldPasswd, newPasswd);
        if(StringUtils.isNotBlank(_changePassword__return)){
        	return JsonKit.json2map(_changePassword__return);
        }
		return new HashMap<String, Object>();
		
	}
	
	/***
	 * 系统登出
	 * @param loginName 用户名
	 * @return “0”：失败；“1”：成功
	 */
	public static Map<String,Object> logout(String loginName){
	    SSOService ss = new SSOService(SSOService.WSDL_LOCATION, SERVICE_NAME);
	    SSOServiceSoap port = ss.getSSOServiceSoap();  
        java.lang.String _logout__return = port.logout(loginName);
        if(StringUtils.isNotBlank(_logout__return)){
        	return JsonKit.json2map(_logout__return);
        }
		return new HashMap<String, Object>();
	}
	public static void SendMessageToUser(){
		
	}
	/**
	 * 
	 * @time   2017年8月11日 下午12:03:23
	 * @author zuoqb
	 * @todo   RTX发送消息拼参数接字符串
	 * @param  @param request
	 * @param  @param dealUrl
	 * @param  @param toUserIds
	 * @return_type   void
	 */
	public static String joisnSendMessageToUserPara(HttpServletRequest request,String dealUrl,String toUserIds){
		User user=RoleUtil.getCurrentUser(request);
		JSONObject r=new JSONObject();
		r.put("TaskTitle","多级上报未处理信息提醒");//消息标题
		r.put("MsgContent",dealUrl);//消息内容
		r.put("TransUrl", dealUrl);//可以为空（消息通知对应的信息系统任务办理界面）
		r.put("UserName",user.getUsername());//发送人姓名
		//接收消息的用户编码列表不能为空，多个用户以逗号分隔；例如:01091,00675,00871
		r.put("UserList",toUserIds);
		r.put("From",request.getSession().getAttribute(FSConstants.SERVER_ID));//发送消息的系统 即授权ID
		/**
		 * 短信提醒类型
		 * 0-- 不发送   1-- RTX 不在线时发送    2-- 始终发送 3--
		 */
		r.put("SMSType",2);
		//r.put("sendSMSTime:", );//发送短信的时间 为空（保留）
		//r.put("BusinessId", );//该消息在发送系统中的主键
		/**
		 * 消息等级
		 * 0=普通等级
			6=重要
			(当参数为0 时，发送的消息
			不会立刻通知给RTX，而是积
			累到一定数据才会发送。当参
			数为6 时会立刻发送消息到
			RTX)
		 */
		r.put("Rank",6);
		return r.toString();
	}
	public static void main(String[] args) {
		/*String info = "{\"Account\":\""+ 1 +"\",\"Password\":\""+ 2 +"\",\"Target\":\""+ 3 +"\"}";
		try {
			 Map<String,Object> m=JsonKit.json2map(info);
			System.out.println(m);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
