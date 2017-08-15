package com.fr.hailian.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.json.JSONObject;

import com.fr.hailian.core.Constants;
import com.fr.hailian.core.DataBaseToolService;
import com.fr.hailian.excel.ImportExcel;
import com.fr.hailian.model.UserModel;
import com.fr.stable.StringUtils;

/**
 * 
 * @className UserService.java
 * @time   2017年8月14日 上午10:32:03
 * @author zuoqb
 * @todo   用户信息维护service
 */
public class UserService {
	/**
	 * 
	 * @time   2017年8月15日 下午1:28:12
	 * @author zuoqb
	 * @todo   根据ID获取用户信息
	 * @param  @param userId
	 * @param  @return
	 * @return_type   UserModel
	 */
	public static UserModel getUserById(Object userId){
		UserModel user=new UserModel();
		String sql="select id,username,password,realname,email from fr_t_user where id='"+userId+"' limit 1 ";
		try {
			String[][] result=DataBaseToolService.getQueryResultBySql(sql);
			 if (result.length>0){
				 user.setId(result[0][0]);
				 user.setUserName(result[0][1]);
				 user.setPassword(result[0][2]);
				 user.setRealName(result[0][3]);
				 user.setEmail(result[0][4]);
			 }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	/**
	 * 
	 * @time   2017年8月14日 上午10:35:21
	 * @author zuoqb
	 * @todo   根据工号判断是否存在
	 * @param  @param userNum
	 * @param  @return
	 * @return_type   boolean
	 */
	public static boolean ifExistsUser(String userNum){
		if(StringUtils.isBlank(userNum)){
			return false;
		}
		String sql="select * from fr_t_user where username='"+userNum+"'";
		
		try {
			return DataBaseToolService.ifExistsBySql(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 
	 * @time   2017年8月14日 上午10:59:21
	 * @author zuoqb
	 * @todo   插入一个员工信息
	 * @param  @param user
	 * @param  @return
	 * @return_type   boolean
	 */
	public static boolean insertUser(UserModel user){
		  String insertsql="insert into fr_t_user(username, password, realname) values ("
                  +"'"+user.getUserName()+"',"
                  +"'"+user.getPassword()+"',"
                  +"'"+user.getUserNum()+"')";
		  try {
			DataBaseToolService.excuteBySql(insertsql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 
	 * @time   2017年8月14日 下午4:45:41
	 * @author zuoqb
	 * @todo   拼接插入语句
	 * @param  @param user
	 * @param  @return
	 * @return_type   String
	 */
	public static String getInsertUserSql(UserModel user){
		  String insertsql="insert into fr_t_user(username, password, realname) values ("
                +"'"+user.getUserName()+"',"
                +"'"+user.getPassword()+"',"
                +"'"+user.getUserNum()+"')";
		  return insertsql;
	}
	
	public static boolean updateUser(UserModel user){
		  String updatesql=" update  fr_t_user set username='"+user.getUserName()+"', password='"+user.getPassword()+"',realname='"+user.getUserNum()+"' ";
		  updatesql+=" where username='"+user.getUserName()+"' ";
		  try {
			DataBaseToolService.excuteBySql(updatesql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 
	 * @time   2017年8月14日 下午3:37:20
	 * @author zuoqb
	 * @todo   读取excel转成实体对象
	 * @param  @param filePath
	 * @param  @return
	 * @return_type   List<UserModel>
	 */
	public static List<UserModel> getExcel2User(String filePath){
		ImportExcel ei;
		/**
		 * 	可同步的数据有工号、姓名、状态、组织机构编码，系统ID
		 */
		try {
			ei = new ImportExcel(filePath, 0);
			return excel2User(ei);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<UserModel> getExcel2User(String fileName,InputStream is){
		ImportExcel ei;
		/**
		 * 	可同步的数据有工号、姓名、状态、组织机构编码，系统ID
		 */
		try {
			ei = new ImportExcel(fileName,is,0, 0);
			return excel2User(ei);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static List<UserModel> excel2User(ImportExcel ei) {
		List<UserModel> userList=new ArrayList<UserModel>();
		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			if(ei.getLastCellNum()>=5){
				UserModel m=new UserModel();
				for (int j = 0; j < ei.getLastCellNum(); j++) {
					String val = ei.getCellValue(row, j)+"";
					switch (j) {
					case 0:
						m.setUserNum(val);
						break;
					case 1:
						m.setUserName(val);
						break;
					case 2:
						m.setFlag(val);
						break;
					case 3:
						m.setOrgCode(val);
						break;
					case 4:
						m.setSysId(val);
						break;
					default:
						break;
					}
				}
				if(StringUtils.isNotBlank(m.getUserName())){
					m.setPassword(Constants.DEFAULT_PWD);
					userList.add(m);
				}
			}
		}
		return userList;
	}
	/**
	 * 
	 * @time   2017年8月14日 下午4:54:06
	 * @author zuoqb
	 * @todo   根据文件路径 导入用户信息
	 * @param  @param filePath
	 * @param  @return
	 * @return_type   JSONObject
	 */
	public static JSONObject importUser(String filePath){
		List<UserModel> userList=getExcel2User(filePath);
		System.out.println("userList:"+userList.toString());
		return operateDbForUser(userList);
	}
	
	public static JSONObject importUser(String fileName,InputStream is){
		List<UserModel> userList=getExcel2User(fileName,is);
		System.out.println("userList:"+userList.toString());
		return operateDbForUser(userList);
	}
	private static JSONObject operateDbForUser(List<UserModel> userList) {
		JSONObject o=new JSONObject();
		if(userList==null||userList.size()==0){
			o.put("fail", true);
			o.put("msg", "Excel内容不能为空！");
		}else{
			String failUser="";//原系统已经存在的用户
			List<String> insertList=new ArrayList<String>();
			for(UserModel u:userList){
				if(!UserService.ifExistsUser(u.getUserName())){
					insertList.add(UserService.getInsertUserSql(u));
				}else{
					failUser+=u.getUserName()+",";
				}
			}
			try {
				DataBaseToolService.excuteBySqlBatch(insertList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			o.put("fail", false);
			o.put("msg", "Excel人员总数量:"+userList.size()+"。其导入成功数量："
			+insertList.size()+",导入失败数量为："+(userList.size()-insertList.size())
			+"。失败用户名称："+failUser+"，失败原因：上述用户已经在系统中存在！");
		}
		return o;
	}
	
	public static void main(String[] args) {
		/*UserModel user=new UserModel("num", "test", "1", "2", "3");
		if(!ifExistsUser(user.getUserName())){
			boolean insert=insertUser(user);
			System.out.println("insert:"+insert);
		}else{
			boolean update=updateUser(user);
			System.out.println("update:"+update);
		}*/
		JSONObject o=UserService.importUser("D:\\test.xlsx");
		System.out.println(o.toString());
	}

}
