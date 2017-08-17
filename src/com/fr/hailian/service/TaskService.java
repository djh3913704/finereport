package com.fr.hailian.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fr.hailian.core.DataBaseToolService;
import com.fr.hailian.model.TaskDetailModel;
import com.fr.hailian.model.UserModel;
import com.fr.stable.StringUtils;

/**
 * 
 * @className TaskService.java
 * @time   2017年8月15日 下午1:11:25
 * @author zuoqb
 * @todo   待办已办
 */
public class TaskService {
	/**
	 * 
	 * @time   2017年8月17日 上午9:31:41
	 * @author zuoqb
	 * @todo  wsdl获取代办已办
	 * @param  @param map
	 * @param  @return
	 * @return_type   JSONObject
	 */
	public static JSONObject getTask(Map<String,Object> map) {
		JSONObject result=new JSONObject();
		UserModel user=UserService.getUserById(map.get("uid"));
		if(user==null){
			result.put("result", 0);
			result.put("memo", "用户不存在！");
		}else{
			//统计总数量
			int allCount=getAllCount(map, user);
			result.put("count", allCount);//记录总条数
			result.put("moreUrl", "");//各业务系统查看跟多内容的链接地址
			//是否查询明细	1：是；0：否（当为1时需返回具体的记录内容；当为0时只需返回查询结果的总条数）
			if("1".equals(map.get("flag"))){
				 List<TaskDetailModel>  detailList=getTaskDetail(map, user);
				 result.put("viewEntries", detailList);//记录集
			}
		}
		result.put("result", 1);
		result.put("memo", "获取数据成功！");
		return result;
	}
	/**
	 * 
	 * @time   2017年8月16日 下午5:53:19
	 * @author zuoqb
	 * @todo   获取wsdl已办待办明细
	 * @param  @param map
	 * @param  @param user
	 * @param  @return
	 * @return_type   List<TaskDetailModel>
	 */
	public static List<TaskDetailModel> getTaskDetail(Map<String,Object> map,UserModel user){
		List<TaskDetailModel> taskList=new ArrayList<TaskDetailModel>();
		int pageSize=10;
		int page=1;
		if(map.get("page")!=null&&StringUtils.isNotBlank(map.get("page")+"")){
			page=Integer.parseInt(map.get("page")+"");
			if(page<=0){
				page=1;
			}
		}
		if(map.get("pageSize")!=null&&StringUtils.isNotBlank(map.get("pageSize")+"")){
			pageSize=Integer.parseInt(map.get("pageSize")+"");
		}
		StringBuffer sb=new StringBuffer();
		sb.append(" select process.name as subject, sendtime,senderid as fromuser,");
		sb.append(" process.createtime as starttime,task_impl.sender as fromusername,  ");
		sb.append("	t_department.name as fromdept,t_user.mobile as tel ");
		sb.append(joinTaskFromSql());
		sb.append(taskSqlWhere(map, user));
		sb.append(" order by createtime,sendtime  asc ");
		sb.append("  limit "+pageSize+" offset "+(page-1)*pageSize);
		try {
			String[][] result=DataBaseToolService.getQueryResultBySql(sb.toString());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(result!=null&&result.length>0){
				for(int x=0;x<result.length;x++){
					/**
					 * 待办链接可以跳转到流程办理页面待办；
					 * 已办链接可以跳转到流程跟踪页面。例如：http://10.0.6.31:8080?workflow=。
					 */
					String url="";
					String sendTime= result[x][1];
					String startTime=result[x][3];
					if(StringUtils.isNotBlank(sendTime)){
						sendTime=sdf.format(sdf.parse(sendTime));
					}
					if(StringUtils.isNotBlank(startTime)){
						startTime=sdf.format(sdf.parse(startTime));
					}
					TaskDetailModel detail=new TaskDetailModel(result[x][0],sendTime, result[x][2],
							startTime, result[x][4], result[x][5], result[x][6],url);
					taskList.add(detail);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskList;
		
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
		String sql=" where task_impl.noderoute like '%"+user.getUserName()+"%' ";
		//请求类型	1:待办事宜；2：已办事宜
		if("1".equals(map.get("type"))){
			sql+=" and task_impl.state in('-1','0','3') ";
		}else if("2".equals(map.get("type"))){
			sql+=" and task_impl.state in('1','2','4') ";
		}
		if(map.get("startTime")!=null&&StringUtils.isNotBlank(map.get("startTime")+"")){
			sql+=" and process.createtime>='"+map.get("startTime")+"' ";
		}
		if(map.get("title")!=null&&StringUtils.isNotBlank(map.get("title")+"")){
			sql+=" and process.name like '%"+map.get("title")+"%' ";
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
}
