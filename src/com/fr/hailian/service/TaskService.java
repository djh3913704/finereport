package com.fr.hailian.service;

import java.sql.SQLException;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fr.hailian.core.DataBaseToolService;
import com.fr.hailian.model.UserModel;
import com.fr.stable.StringUtils;

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
	public static void getTaskDetail(Map<String,Object> map,UserModel user){
		int pageSize=10;
		int page=1;
		if(map.get("Page")!=null&&StringUtils.isNotBlank(map.get("Page")+"")){
			page=Integer.parseInt(map.get("Page")+"");
		}
		if(map.get("Pagesize")!=null&&StringUtils.isNotBlank(map.get("Pagesize")+"")){
			pageSize=Integer.parseInt(map.get("Pagesize")+"");
		}
		StringBuffer sb=new StringBuffer();
		sb.append(" select process.name as subject, sendtime,senderid as fromuser,");
		sb.append(" process.createtime as starttime,task_impl.sender as fromusername,  ");
		sb.append("	t_department.name as fromdept,t_user.mobile as tel ");
		sb.append(joinTaskFromSql());
		sb.append(taskSqlWhere(map, user));
		sb.append(" order by createtime,sendtime  asc ");
		sb.append("  limit "+pageSize+" offset "+(page-1)*pageSize+"   ");
		
	}
	/**
	 * 
	 * @time   2017年8月15日 下午5:29:36
	 * @author zuoqb
	 * @todo   统计符合条件的全部数量
	 * @param  @param map
	 * @param  @param user
	 * @param  @return
	 * @return_type   int
	 */
	public static int getAllCount(Map<String,Object> map,UserModel user){
		String sql=" select count(1) ";
		sql+=joinTaskFromSql()+taskSqlWhere(map, user);
		try {
			String[][] result=DataBaseToolService.getQueryResultBySql(sql);
			if(result!=null&&result.length>0){
				return Integer.parseInt(result[0][0]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
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
	public static String taskSqlWhere(Map<String,Object> map,UserModel user){
		String sql=" task_impl.noderoute LIKE '%"+user.getUserName()+"%' ";
		//请求类型	1:待办事宜；2：已办事宜
		if("1".equals(map.get("Type"))){
			sql+=" and task_impl.state in('-1','0','3') ";
		}else if("2".equals(map.get("Type"))){
			sql+=" and task_impl.state in('1','2','4') ";
		}
		if(map.get("StartTime")!=null&&StringUtils.isNotBlank(map.get("StartTime")+"")){
			sql+=" and process.createtime>='"+map.get("StartTime")+"' ";
		}
		if(map.get("Title")!=null&&StringUtils.isNotBlank(map.get("Title")+"")){
			sql+=" process.name like '%"+map.get("Title")+"%' ";
		}
		return sql;
	}
	/**
	 * 
	 * @time   2017年8月15日 下午5:23:04
	 * @author zuoqb
	 * @todo   拼接关联表sql
	 * @param  @return
	 * @return_type   String
	 */
	public static String joinTaskFromSql(){
		StringBuffer sb=new StringBuffer();
		sb.append(" from fr_process_task_impl task_impl  ");
		sb.append(" left join fr_report_process_task process_task on process_task.id=task_impl.taskid ");
		sb.append(" left join fr_report_process process on process.id=process_task.processid ");
		sb.append(" left join fr_t_user t_user on t_user.id=task_impl.senderid ");
		sb.append(" left join fr_t_department_post_user department_post_user on department_post_user.userid=t_user.id ");
		sb.append(" left join fr_t_department t_department  on t_department.id=department_post_user.departmentid ");
		return sb.toString();
	}
	public static void main(String[] args) {
		try {
			String[][] result=DataBaseToolService.getQueryResultBySql("SELECT COUNT(1) FROM FR_PROCESS_TASK_IMPL");
			System.out.println(result[0][0]);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
