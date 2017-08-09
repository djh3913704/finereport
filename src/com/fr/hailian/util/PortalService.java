package com.fr.hailian.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

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
		URL wsdlURL = SSOService.WSDL_LOCATION;
	    SSOService ss = new SSOService(wsdlURL, SERVICE_NAME);
	    SSOServiceSoap port = ss.getSSOServiceSoap();  
        java.lang.String _userValidate__return = port.userValidate(info);
        System.out.println("userValidate.result=" + _userValidate__return);
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
		URL wsdlURL = SSOService.WSDL_LOCATION;
	    SSOService ss = new SSOService(wsdlURL, SERVICE_NAME);
	    SSOServiceSoap port = ss.getSSOServiceSoap();
	    java.lang.String _getTargetEntry__return = port.getTargetEntry(info);
        System.out.println("getTargetEntry.result=" + _getTargetEntry__return);
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
		URL wsdlURL = SSOService.WSDL_LOCATION;
	    SSOService ss = new SSOService(wsdlURL, SERVICE_NAME);
	    SSOServiceSoap port = ss.getSSOServiceSoap();
	    java.lang.String userInfo=port.getUserInfoByToken(info);
	    if(StringUtils.isNotBlank(userInfo)){
        	return JsonKit.json2map(userInfo);
        }
		return new HashMap<String, Object>();
		
	}
	public static void main(String[] args) {
		String info = "{\"Account\":\""+ 1 +"\",\"Password\":\""+ 2 +"\",\"Target\":\""+ 3 +"\"}";
		try {
			 Map<String,Object> m=JsonKit.json2map(info);
			System.out.println(m);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
