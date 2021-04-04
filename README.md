# 基于Java SSH框架的在线音乐播放网站

为什么使用SSH，不用SSM或者SpringBoot，原因就是选题时间太早，还没学到-_-!因为是自学的。。

## 系统功能结构
本系统主要分为五大功能模块。
1. 用户操作：主要包括用户登录、注册和用户个人信息修改的功能，此外用户还可以收藏喜欢的歌单，评论歌单。
2. 用户浏览：全部页面开发完成之后，用户可以随意浏览歌单、歌手、专辑和榜单数据，用户还可以查看自己收藏的音乐。
3. 音乐播放：使用开源网页播放器，只需提供各种歌曲后台数据接口，即可播放。
4. 搜索功能：对关键字进行分词，根据分词结果进行查询，对查询进行了优化。
5. 猜歌功能：闲暇之余，用户可以尝试系统提供的猜歌小游戏功能，来验证下自己是否有“中华小曲库”之称。

![image](https://user-images.githubusercontent.com/31727281/113501557-a0101000-9558-11eb-9429-68eb86184f39.png)


## 开发工具及相关中间件
IntelliJ IDEA,Maven,SQLyog,Tomcat,Docker,FastDFS,IK Analyzer,Redis

## 项目运行相关介绍
1. 相关中间件的部署都是在我的华为云里使用docker部署的
    在Resource文件夹里的DockerRun.txt有各个中间件的启动命令。只要你使用docker拉起相关镜像，使用启动命令启动即可。
    我的华为云到期了，配置文件中IP端口，用户名密码啥的我也就不用隐藏了。
2. Resource文件夹下的back.sql是我之前爬取音乐数据到数据库的实例数据，不需要在爬取了。直接在mysql里面导入sql即可。
3. Resource文件夹下的proc_rank.sql是几个模拟访问热歌的存过，导入即可。
4. 然后把你的数据库，redis等环节替换完成启动访问首页index即可。

## 网站相关模块截图
![image](https://user-images.githubusercontent.com/31727281/113501776-6f30da80-955a-11eb-82ac-f2b2ba8c377f.png)
![image](https://user-images.githubusercontent.com/31727281/113501777-722bcb00-955a-11eb-9207-b9ca49c3e895.png)
![image](https://user-images.githubusercontent.com/31727281/113501778-748e2500-955a-11eb-9880-b15ae0742ab6.png)
![image](https://user-images.githubusercontent.com/31727281/113501782-77891580-955a-11eb-958e-079408dcdc0a.png)
![image](https://user-images.githubusercontent.com/31727281/113501783-7952d900-955a-11eb-864b-6fb120644594.png)
![image](https://user-images.githubusercontent.com/31727281/113501788-7fe15080-955a-11eb-8ec9-686efb9a00a1.png)
![image](https://user-images.githubusercontent.com/31727281/113501791-81ab1400-955a-11eb-985b-618f002c4cad.png)
![image](https://user-images.githubusercontent.com/31727281/113501798-8a034f00-955a-11eb-80ba-663f5931d9a0.png)
![image](https://user-images.githubusercontent.com/31727281/113501802-8c65a900-955a-11eb-8e56-6127908de33b.png)
![image](https://user-images.githubusercontent.com/31727281/113501804-8f609980-955a-11eb-9733-7c2533aba9bc.png)
![image](https://user-images.githubusercontent.com/31727281/113501805-91c2f380-955a-11eb-9892-1bc1229f7736.png)
![image](https://user-images.githubusercontent.com/31727281/113501812-98ea0180-955a-11eb-92b4-648fa79d094b.png)
![image](https://user-images.githubusercontent.com/31727281/113501815-9d161f00-955a-11eb-8133-cc25c1fcebd9.png)
![image](https://user-images.githubusercontent.com/31727281/113501819-a1423c80-955a-11eb-952e-f2acc2f971dc.png)
![image](https://user-images.githubusercontent.com/31727281/113501823-a8694a80-955a-11eb-8e04-90e233217e9d.png)
