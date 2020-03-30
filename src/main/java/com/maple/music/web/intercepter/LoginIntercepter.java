package com.maple.music.web.intercepter;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author LiangDong
 * @Date 2020/3/28
 */
public class LoginIntercepter extends AbstractInterceptor {
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		ActionContext invocationContext = actionInvocation.getInvocationContext();
		Object user = invocationContext.getSession().get("user");
		HttpServletResponse response = ServletActionContext.getResponse();
		if(user!=null){
			return actionInvocation.invoke();
		}else{
			return Action.LOGIN;
		}
	}
}
