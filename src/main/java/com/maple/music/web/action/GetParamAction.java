package com.maple.music.web.action;

import com.maple.music.entity.User;
import com.maple.music.util.ResultUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2020/2/24
 * @Description 获取session中的参数返回前端，前缀统一 getParam_xxx  xxx指方法名；
 */
@Controller("getParamAction")
@Scope("prototype")
public class GetParamAction extends ActionSupport {
	private final static Logger logger = LoggerFactory.getLogger(GetParamAction.class);

	/**
	 * 获取session中的数据返回到前端
	 * @return
	 */
	public String user(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		HttpServletResponse response = ServletActionContext.getResponse();
		Map<String,Object> map = new HashMap<String,Object>();
		User user = (User) session.get("user");
		if(user!=null){
			logger.info("=====当前session中用户为=====");
			logger.info("====="+user.getUsername()+"=====");
			map.put("user",user);
			map.put("resultStatus",true);
		}else{
			map.put("resultStatus",false);
		}

		try {
			ResultUtils.toJson(response,map);
		} catch (IOException e) {
			logger.error("无法将user转为Json数据！！");
		}
		return null;
	}
}
