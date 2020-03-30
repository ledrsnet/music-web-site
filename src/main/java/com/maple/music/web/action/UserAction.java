package com.maple.music.web.action;

import com.maple.music.entity.User;
import com.maple.music.service.UserService;
import com.maple.music.util.CodeUtil;
import com.maple.music.util.MD5;
import com.maple.music.util.ResultUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LiangDong
 * @Date 2019/11/20
 */
@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User> {
	private static final Logger logger = LoggerFactory.getLogger(UserAction.class);
	//定义存放到值栈中的对象
	private User user = new User();

	@Override
	public User getModel() {
		return user;
	}

	// 接受页面的验证码
	private String checkCode;

	// 提供验证码的setter方法
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}


	//依赖service
	@Resource
	private UserService userService;

	/**
	 * 测试用
	 * @return
	 */
	public String m1(){
		user =  userService.getUser(1L);
		System.out.println(user.getUsername());
		return SUCCESS;
	}

	/**
	 * 用户注册，保存用户
	 * @return
	 */
	public String saveUser(){
		Date date =new Date();
		user.setCreatTime(date);
		user.setLastTime(date);
		user.setState(1);
		String password = user.getPassword();
		String encryptPassword = MD5.encryptPassword(password, "123");
		user.setPassword(encryptPassword);
		Map<String,Object> map = new HashMap<>();
		try {
			userService.saveUser(user);
			map.put("resultStatus",true);
		} catch (Exception e) {
			logger.error("保存用户异常");
			map.put("resultStatus",false);
		}
		try {
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * 用户登录验证
	 * @return
	 */
	public String login(){
		// 验证码校验
		boolean flag = CodeUtil.checkVerifyCode(ServletActionContext.getRequest(), checkCode);
		Map<String,Object> map = new HashMap<>();

		if(flag==true){
			String encryptPassword = MD5.encryptPassword(this.user.getPassword(), "123");
			User resUser = userService.getUserByName(this.user.getUsername());
			if (resUser != null&&resUser.getPassword().equals(encryptPassword)) {
				// 将用户加入session
				ActionContext.getContext().getSession().put("user",resUser);
				logger.info("=====用户："+resUser.getUsername()+"登录成功=====");
				map.put("resultStatus",true);
				map.put("resultMsg","登录成功");
			}else{
				map.put("resultStatus",false);
				map.put("resultMsg","用户名或密码错误！");
			}

		}else{
			map.put("resultStatus",false);
			map.put("resultMsg","验证码错误！");
		}
		try {
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (IOException e) {
			logger.error("用户登录成功返回值转换失败");
		}
		return null;
	}

	/**
	 * 用户注销方法
	 * @return
	 */
	public String logout(){
		ActionContext.getContext().getSession().remove("user");
		return SUCCESS;
	}

	/**
	 * 校验昵称是否存在
	 * @return
	 */
	public String checkNickname(){
		boolean b = userService.checkNickName(user.getNickname());
		Map<String,Object> map = new HashMap<>();
		map.put("resultStatus",b);
		try {
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (IOException e) {
			logger.error("校验昵称是否存在结构转换json出错");
		}
		return null;
	}

	/**
	 * 校验用户名是否存在
	 * @return
	 */
	public String checkUsername(){
		User resUser = userService.getUserByName(this.user.getUsername());
		Map<String,Object> map = new HashMap<>();
		if(resUser!=null){
			map.put("resultStatus",false);
		}else{
			map.put("resultStatus",true);
		}
		try {
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (IOException e) {
			logger.error("校验用户名是否存在结果转换json出错");
		}
		return null;
	}

	public String getUserFavorite(){
		Map<String,Object> map = new HashMap<>();
		User user = (User) ActionContext.getContext().getSession().get("user");

		if(user==null){
			map.put("code",500);
		}else{
			map.put("code",200);
			List<Map<String,Object>> list = userService.getUserFavorite(user.getUserId());
			map.put("data",list);
		}
		try {
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toMyMusic(){
		return "success";
	}

	public String deSubscrib(){
		Map<String,Object> map = new HashMap<>();
		try {
			User user = (User) ActionContext.getContext().getSession().get("user");
			String playlistId = ServletActionContext.getRequest().getParameter("playlistId");
			if(user!=null){
				int flag = userService.deSubscrib(user.getUserId(), new BigInteger(playlistId));
				if(flag==200){
					map.put("code",200);
				}else{
					map.put("code",500);
				}
			}else{
				map.put("code",500);
			}
			ResultUtils.toJson(ServletActionContext.getResponse(),map);
		} catch (Exception e) {
			map.put("code",500);
			try {
				ResultUtils.toJson(ServletActionContext.getResponse(),map);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		return null;
	}
}
