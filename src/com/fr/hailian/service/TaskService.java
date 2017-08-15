package com.fr.hailian.service;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fr.hailian.model.UserModel;

/**
 * 
 * @className TaskService.java
 * @time   2017年8月15日 下午1:11:25
 * @author zuoqb
 * @todo   已办待办
 */
public class TaskService {
	public static void getTask(Map<String,Object> map) {
		JSONObject result=new JSONObject();
		UserModel user=UserService.getUserById(map.get("Uid"));
		if(user==null){
			result.put("Result", 0);
			result.put("Memo", "用户不存在！");
		}else{
			//统计总数量
			
			//是否查询明细	1：是；0：否（当为1时需返回具体的记录内容；当为0时只需返回查询结果的总条数）
			if("1".equals(map.get("Flag"))){
				
			}else{
				
			}
		}
		
	}
	public static void getAllCount(){
		
	}
	/**
	 * 
	 * @time   2017年8月15日 下午1:44:58
	 * @author zuoqb
	 * @todo   根据参数拼接查询条件
	 * @param  @param map
	 * @param  @return
	 * @return_type   String
	 */
	public static String taskSqlWhere(Map<String,Object> map){
		String sql="";
		
		return sql;
	}
}
