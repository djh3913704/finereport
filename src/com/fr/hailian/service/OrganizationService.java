package com.fr.hailian.service;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.json.JSONObject;

import com.fr.hailian.core.DataBaseToolService;
import com.fr.hailian.excel.ImportExcel;
import com.fr.hailian.model.OrganizationModel;

/**
 * 
 * @className OrganizationService.java
 * @time   2017年8月14日 上午10:32:03
 * @author zuoqb
 * @todo   组织机构信息维护service
 */
public class OrganizationService {
	/**
	 * 
	 * @time   2017年8月14日 上午10:35:21
	 * @author zuoqb
	 * @todo   判断是否存在
	 * @param  @param userNum
	 * @param  @return
	 * @return_type   boolean
	 */
	public static boolean ifExistsOrganization(OrganizationModel org){
		if(org==null){
			return false;
		}
		String sql="select * from fr_t_department where id='"+org.getId()+"' and name='"+org.getName()+"' ";
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
	 * @todo   插入一个组织信息
	 * @param  @param OrganizationModel
	 * @param  @return
	 * @return_type   boolean
	 */
	public static boolean insertOrganization(OrganizationModel org){
		  String insertsql=getInsertOrganizationSql(org);
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
	public static String getInsertOrganizationSql(OrganizationModel org){
		  String insertsql="insert into fr_t_department(id,pid,name, description) values ("
                  +"'"+org.getId()+"',"
                   +"'"+org.getPid()+"',"
                    +"'"+org.getName()+"',"
                  +"'"+org.getDes()+"')";
		  return insertsql;
	}
	/**
	 * 
	 * @time   2017年8月15日 上午9:48:23
	 * @author zuoqb
	 * @todo   更新数据
	 * @param  @param org
	 * @param  @return
	 * @return_type   boolean
	 */
	public static boolean updateOrganization(OrganizationModel org){
		  String updatesql=getUpdateOrganizationSql(org);
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
	 * @time   2017年8月14日 下午4:45:41
	 * @author zuoqb
	 * @todo   拼接更新语句
	 * @param  @param user
	 * @param  @return
	 * @return_type   String
	 */
	public static String getUpdateOrganizationSql(OrganizationModel org){
		 String updatesql=" update  fr_t_department set  pid='"+org.getPid()+"', name='"+org.getName()+"', description='"+org.getDes()+"' ";
		  updatesql+=" where id='"+org.getId()+"' ";
		  return updatesql;
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
	public static List<OrganizationModel> getExcel2Organization(String filePath){
		ImportExcel ei;
		try {
			ei = new ImportExcel(filePath, 0);
			return excel2Organization(ei);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static List<OrganizationModel> getExcel2Organization(String fileName,InputStream is){
		ImportExcel ei;
		try {
			ei = new ImportExcel(fileName,is,0, 0);
			return excel2Organization(ei);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @time   2017年8月15日 上午9:50:30
	 * @author zuoqb
	 * @todo   将excel数据转成实体集合
	 * @param  @param ei
	 * @param  @return
	 * @return_type   List<OrganizationModel>
	 */
	private static List<OrganizationModel> excel2Organization(ImportExcel ei) {
		List<OrganizationModel> orgList=new ArrayList<OrganizationModel>();
		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum()+1; i++) {
			Row row = ei.getRow(i);
			if(ei.getLastCellNum()>=5){
				OrganizationModel m=new OrganizationModel();
				for (int j = 0; j < ei.getLastCellNum(); j++) {
					String val = ei.getCellValue(row, j)+"";
					switch (j) {
					case 0:
						m.setId(val);
						break;
					case 1:
						m.setPid(val);
						break;
					case 4:
						m.setName(val);
						m.setDes(val);
						break;
					default:
						break;
					}
				}
				orgList.add(m);
			}
		}
		return orgList;
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
	public static JSONObject importOrganization(String filePath){
		List<OrganizationModel> userList=getExcel2Organization(filePath);
		return operateDbForOrganization(userList);
	}
	
	public static JSONObject importOrganization(String fileName,InputStream is){
		List<OrganizationModel> userList=getExcel2Organization(fileName,is);
		return operateDbForOrganization(userList);
	}
	private static JSONObject operateDbForOrganization(List<OrganizationModel> orgList) {
		JSONObject o=new JSONObject();
		if(orgList==null||orgList.size()==0){
			o.put("fail", true);
			o.put("msg", "Excel内容不能为空！");
		}else{
			List<String> insertList=new ArrayList<String>();
			List<String> updateList=new ArrayList<String>();//原系统已经存在的组织机构
			for(OrganizationModel org:orgList){
				if(!OrganizationService.ifExistsOrganization(org)){
					insertList.add(OrganizationService.getInsertOrganizationSql(org));
				}else{
					updateList.add(getUpdateOrganizationSql(org));
				}
			}
			try {
				if(insertList.size()>0){
					DataBaseToolService.excuteBySqlBatch(insertList);
				}
				if(updateList.size()>0){
					DataBaseToolService.excuteBySqlBatch(updateList);
				}
				o.put("fail", false);
				String msg="Excel组织机构总数量:"+orgList.size()+"。导入成功数量："+(insertList.size()+updateList.size());
				if(insertList.size()>0){
					msg+=",其中新增数量为："+insertList.size();
				}
				if(updateList.size()>0){
					msg+="。更新数量为："+updateList.size();
				}
				o.put("msg",msg);
			} catch (Exception e) {
				e.printStackTrace();
				o.put("fail", true);
				o.put("msg", "导入失败，请检查Excel格式以及导入类型");
			}
			
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
		JSONObject o=OrganizationService.importOrganization("D:\\组织机构.xlsx");
		System.out.println(o.toString());
	}

}
