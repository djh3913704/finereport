package com.fr.hailian.service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fr.fs.base.entity.User;
import com.fr.fs.control.UserControl;
import com.fr.hailian.core.BaseServlet;
import com.fr.hailian.core.Constants;
import com.fr.hailian.core.DataBaseToolService;
import com.fr.hailian.model.TaskDetailModel;
import com.fr.hailian.model.UserModel;
import com.fr.hailian.util.DESSymmetricEncoder;
import com.fr.hailian.util.HttpClientUtil;
import com.fr.hailian.util.JsonKit;
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
		sb.append("	t_department.name as fromdept,t_user.mobile as tel ,process_node.reportcontrol,process_task.id as taskId,task_impl.id as taskImpId ");
		sb.append(joinTaskFromSql());
		sb.append(taskSqlWhere(map, user));
		sb.append(" order by createtime,sendtime  asc ");
		sb.append("  limit "+pageSize+" offset "+(page-1)*pageSize);
		//生成url地址 发送RTX信息使用
		String sign=DESSymmetricEncoder.createSign(user.getId()+"");
		try {
			String[][] result=DataBaseToolService.getQueryResultBySql(sb.toString());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(result!=null&&result.length>0){
				for(int x=0;x<result.length;x++){
					/**
					 * 待办链接可以跳转到流程办理页面待办；
					 * 已办链接可以跳转到流程跟踪页面。例如：http://10.0.6.31:8080?workflow=。
					 */
					String url = joinTaskUrl(user, sign,result[x][7],result[x][8],result[x][9]);
					/**
					 * [{"reportPath":"Poly2.cpt","parameters":[],"operator":":1##1##测试2姓名##测试1姓名"}]
					 */
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
		sb.append(" left join fr_report_process_node process_node on process_node.processid=process.id ");
		sb.append(" left join fr_t_user t_user on t_user.id=task_impl.senderid ");
		sb.append(" left join fr_t_department_post_user department_post_user on department_post_user.userid=t_user.id ");
		sb.append(" left join fr_t_department t_department  on t_department.id=department_post_user.departmentid ");
		return sb.toString();
	}
	/**
	 * 
	 * @time   2017年8月17日 下午3:28:44
	 * @author zuoqb
	 * @todo   拼接已办待办url
	 * @param  @param user
	 * @param  @param sign
	 * @param  @param reportcontrol
	 * @param  @return
	 * @param  @throws Exception
	 * @return_type   String
	 */
	private static String joinTaskUrl(UserModel user, String sign,String reportcontrol,String taskId,String taskImpId)
			throws Exception {
		String path="/rtxSecurityServlet?userId="+user.getId()+"&sign="+sign;
		String hl_url=Constants.CTX_PATH+"/ReportServer?reportlet="+reportPath(reportcontrol)+"&op=write&__cutpage__=null&__processtaskid__="+taskImpId+"&__allprocesstaskid__="+taskId;
		//hl_url=java.net.URLEncoder.encode(hl_url, "UTF-8");
		hl_url=hl_url.replaceAll("&", "@@");
		String url=path+"&hl_url="+hl_url;
		url=java.net.URLEncoder.encode(url, "UTF-8");
		return url;
	}
	/**
	 * 
	 * @time   2017年8月17日 下午3:51:18
	 * @author zuoqb
	 * @todo   具体上报页面
	 * @param  @param json
	 * @param  @return
	 * @return_type   String
	 */
	public static String reportPath(String json){
		if(StringUtils.isBlank(json)){
			return "";
		}
		String path="";
		List<Map<String,Object>> list=JsonKit.json2listmap(json);
		for(Map<String,Object> map:list){
			if(map.get("reportPath")!=null){
				path=map.get("reportPath").toString();
			}
		}
		return path;
	}
	/**
	 * 
	 * @time   2017年8月18日 下午5:26:36
	 * @author zuoqb
	 * @todo   获取审核人
	 * @param  @param taskImplId //上报流程中的任务下发出来的具体任务ID  表fr_process_task_impl
	 * @param  @return
	 * @return_type   List<User>
	 */
	public static List<User> getShareUser(String taskImplId){
		//http://localhost:8075/WebReport/ReportServer?op=report_process&cmd=get_taskImpl&Fri%20Aug%2018%202017%2014:12:41%20GMT+0800%20(%D6%D0%B9%FA%B1%EA%D7%BC%CA%B1%BC%E4)&taskId=23
		//http://localhost:8075/WebReport/ReportServer?op=report_process&cmd=get_taskImpl&Fri Aug 18 2017 14:12:41 GMT 0800 (�й���׼ʱ��)&taskId=23
		String taskUrl="http://"+BaseServlet.getIpAddress()+":"+Constants.CTX_PORT+Constants.CTX_PATH+"/ReportServer?op=report_process&cmd=get_taskImpl&taskId="+taskImplId;
		return nodeInfo(taskUrl);
	}
	
	private static List<User>  nodeInfo(String taskUrl) {
		List<User>  userList=new ArrayList<User>();
		String result=HttpClientUtil.sendGetRequest(taskUrl,null);
		Map<String,Object> data=JsonKit.json2map(result);
		int currentNodeIdx=Integer.valueOf(data.get("currentNodeIdx")==null?"0":data.get("currentNodeIdx").toString());
		List<Map<String,Object>> nodes=JsonKit.json2listmap(JsonKit.json2map(data.get("process").toString()).get("nodes").toString());
		if(nodes!=null&&nodes.size()>=currentNodeIdx+1){
			//取下一个节点
			String reportControl=nodes.get(currentNodeIdx+1).get("reportControl")+"";
			List<Map<String,Object>> rep=JsonKit.json2listmap(reportControl);
			//"operatorName":"用户:韩文(hanwen),孙林(sunlin),王伟(wangwei),张珊(zhangshan)",
			String[] operatorName=rep.get(0).get("operatorName").toString().replaceFirst("用户:", "").split(",");
			for(String name:operatorName){
				String userName=name.substring(name.indexOf("(")+1,name.indexOf(")"));
				try {
					User user=UserControl.getInstance().getByUserName(userName);
					userList.add(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return userList;
	}
	
	public static void main(String[] args) throws Exception {
		/*String url="%2FrtxSecurityServlet%3FuserId%3D32%26sign%3Dd%25252FLWhfP96RBD5eWLLRoJezGZecZkrgZweFR0KQclwL0Jyw7jFIMnfu0H5XgH1P%25252BdFi3%25252Bs1btBjM5%25250D%25250Aq56U3lbHrS56%25252BvtTEMxkYsTOok2HWzE75kyyWTb2tg%25253D%25253D%26hl_url%3D%2FWebReport%2FReportServer%3Freportlet%3Ddoc%2FForm%2FCutpage%2FCutpage.cpt%40%40op%3Dwrite%40%40__cutpage__%3Dnull%40%40__processtaskid__%3D58%40%40__allprocesstaskid__%3D18";
		url=java.net.URLDecoder.decode(url, "UTF-8");
		System.out.println(url);
		List<Map<String,Object>> list=JsonKit.json2listmap("[{\"reportPath\":\"Poly2.cpt\",\"parameters\":[],\"operator\":\":1##1##测试2姓名##测试1姓名\"}]");
		for(Map<String,Object> map:list){
			System.out.println(map.get("reportPath"));
			System.out.println(map.get("operator"));
		}*/
		String taskUrl="http://localhost:8075"+Constants.CTX_PATH+"/ReportServer?op=report_process&cmd=get_taskImpl&taskId=23";
		nodeInfo(taskUrl);
	}
}
