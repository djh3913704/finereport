package com.fr.hailian.util;

import java.util.Map;

import com.fr.fs.base.entity.User;
import com.fr.fs.control.UserControl;

public class test {

	public static void main(String[] args){
		String token = "FEEE591E3B55320B7038E74D4E4EFE86";
		String redictUrl = "F047F50A72B04A049D8436009";
		Map<String,Object> result = PortalService.getUserInfoByToken(token, redictUrl);
		if(result!=null&&"1".equals(result.get("Result"))){
			String name = (String) result.get("Memo");
			try {
				User user = UserControl.getInstance().getByUserName(name);
				if(user!=null&&RoleUtil.judgeAuxiliaryRole(user)){
					
					//RoleUtil.loginCMD(hrequest, response);
					//resonse.sendRedirect(redictUrl);
					System.out.println("成功");
				}else{
					//r.put("fail", true);
					//r.put("msg", "该用户没有辅助决策系统权限，请联系管理员!");
					System.out.println("该用户没有辅助决策系统权限，请联系管理员");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			//r.put("fail", true);
			//r.put("msg", "根据token获取用户信息失败!");
			System.out.println("根据token获取用户信息失败!");
		}
	}
}
