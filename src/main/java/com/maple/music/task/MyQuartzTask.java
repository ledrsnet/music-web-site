package com.maple.music.task;

import com.maple.music.service.UserService;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyQuartzTask {

    @Resource
    public UserService userService;

    //cron任务触发器运行的任务,每一分钟查询数据库一次，防止服务器防火墙中断数据库TCP连接
    public void doCronTask() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("doCronTask正在运行..." + sdf.format(new Date())+userService.keepAlive());
    }
}
