package com.fr.hailian.wsdl;

import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.alibaba.fastjson.JSONObject;
import com.fr.hailian.util.JsonKit;
import com.fr.stable.StringUtils;
/**
 * 
 * @className HaveDoneWebService.java
 * @time   2017年8月13日 下午6:31:38
 * @author zuoqb
 * @todo   统一待办 已办WebService接口
 */
@WebService
public class HaveDoneWebService {
	@SuppressWarnings("finally")
	public String getTask(@WebParam(name = "info", mode = WebParam.Mode.IN) String info) {
		JSONObject result=new JSONObject();
		if(StringUtils.isBlank(info)){
			result.put("Result", 0);
			result.put("Memo", "参数不能为空！");
			return result.toString();
		}
		try {
			Map<String,Object> map=JsonKit.json2map(info);
			if(map.get("Uid")==null||StringUtils.isBlank(map.get("Uid")+"")){
				result.put("Result", 0);
				result.put("Memo", "请输入用户ID");
			}
		} catch (Exception e) {
			result.put("Result", 0);
			result.put("Memo", "请输入正确的json格式");
		}finally{
			return result.toString();
		}
	}
	public static void main(String[] args) {
		//{"Flag":"1","Page":1,"Pagesize":10,"Type":"1","Uid":"2"}
		JSONObject o=new JSONObject();
		o.put("Uid", "31");//用户账号
		o.put("Type","1");//请求类型	1:待办事宜；2：已办事宜
		o.put("Flag","1");//是否查询明细	1：是；0：否（当为1时需返回具体的记录内容；当为0时只需返回查询结果的总条数）
		o.put("StartTime",null);//流程发起时间	返回流程发起时间在starttime至今之间的数据（为null时查询所有）
		o.put("Title",null);//流程标题	返回标题中带有title的数据（为null时查询所有）
		o.put("Page",1);//第几页	根据page请求第几页的数据。
		o.put("Pagesize",10);//每页大小	每页请求的条数（例如每页10条）
		System.out.println(o.toString());
	}
}
