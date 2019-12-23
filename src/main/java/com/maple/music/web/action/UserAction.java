package com.maple.music.web.action;

import com.maple.music.entity.User;
import com.maple.music.service.UserService;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @author LiangDong
 * @Date 2019/11/20
 */
@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport {
	//定义存放到值栈中的对象
	private User user;

    //依赖service
	@Resource
	private UserService userService;

	//实现要存放到值栈中对象的get方法
	public User getUser() {
		return user;
	}

	public String m1(){
		user =  userService.getUser(1L);
		System.out.println(user.getUsername());
		return SUCCESS;
	}

	public String saveUser(){
		User user = new User();

		user.setUsername("事务提交");
		userService.saveUser(user);

		return SUCCESS;
	}
}
