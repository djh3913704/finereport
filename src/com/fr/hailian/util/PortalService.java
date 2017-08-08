package com.fr.hailian.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import com.fr.hailian.webservice.SSOService;
import com.fr.hailian.webservice.SSOServiceSoap;

public class PortalService {
	
	private static final QName SERVICE_NAME = new QName("http://sso.nwh.cn", "SSOService");
	
	/***
	 * 校验用户
	 * @param account 用户名
	 * @param password 密码
	 * @param form sysid
	 * @return
	 */
	public String userValidate(String account,String password,String form){
		//拼接字符串
		String info = "{\"Account\":\""+ account +"\",\"Password\":\""+ password +"\",\"From\":\""+ form +"\"}";
		URL wsdlURL = SSOService.WSDL_LOCATION;
	    SSOService ss = new SSOService(wsdlURL, SERVICE_NAME);
	    SSOServiceSoap port = ss.getSSOServiceSoap();  
        java.lang.String _userValidate__return = port.userValidate(info);
        System.out.println("userValidate.result=" + _userValidate__return);
		return _userValidate__return;
		
	}
	
	/***
	 * 
	 * @param account 用户名
	 * @param password userValidate返回的token口令
	 * @param target 
	 * @return
	 */
	public String getTargetEntry(String account,String password,String target){
		//拼接字符串
		String info = "{\"Account\":\""+ account +"\",\"Password\":\""+ password +"\",\"Target\":\""+ target +"\"}";
		URL wsdlURL = SSOService.WSDL_LOCATION;
	    SSOService ss = new SSOService(wsdlURL, SERVICE_NAME);
	    SSOServiceSoap port = ss.getSSOServiceSoap();
	    java.lang.String _getTargetEntry__return = port.getTargetEntry(info);
        System.out.println("getTargetEntry.result=" + _getTargetEntry__return);
		return _getTargetEntry__return;
		
	}
	
	/***
	 * 
	 * @param token userValidate返回的token口令
	 * @param sysID
	 * @return
	 */
	public String getUserInfoByToken(String token, String sysID){
		URL wsdlURL = SSOService.WSDL_LOCATION;
	    SSOService ss = new SSOService(wsdlURL, SERVICE_NAME);
	    SSOServiceSoap port = ss.getSSOServiceSoap();
		return null;
		
	}

}
