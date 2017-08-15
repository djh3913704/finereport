package com.fr.hailian.model;

import java.io.Serializable;

import com.fr.hailian.core.Constants;

/**
 * 
 * @className UserModel.java
 * @time   2017年8月14日 上午10:53:42
 * @author zuoqb
 * @todo   用户实体
 */
public class UserModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6201547131958870064L;
	private String id;//主键ID
	private String userNum;//员工编号
	private String userName;//员工姓名
	private String password;//密码  默认123456
	private String flag;//状态
	private String orgCode;//组织编码
	private String sysId;//系统ID
	private String realName;
	private String email;
	public UserModel(){
		super();
	}
	public UserModel(String userNum, String userName, String flag,
			String orgCode, String sysId) {
		super();
		this.userNum = userNum;
		this.userName = userName;
		this.flag = flag;
		this.orgCode = orgCode;
		this.sysId = sysId;
		this.password=Constants.DEFAULT_PWD;
	}
	
	public UserModel(String userNum, String userName, String flag,
			String orgCode, String sysId, String password) {
		super();
		this.userNum = userNum;
		this.userName = userName;
		this.flag = flag;
		this.orgCode = orgCode;
		this.sysId = sysId;
		this.password = password;
	}
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getSysId() {
		return sysId;
	}
	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
}
