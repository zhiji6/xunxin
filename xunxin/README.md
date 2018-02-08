# app
循心是一款社交软件
## app

功能模块：
xunxin.web：发布功能的web项目
xunxin.core：存放service层、controller层：mongo服务接口的实现、config配置层
xunxin.mongodb的操作实现
xunxin.api：各个服务接口、mapper层
xunxin.util：工具类集合
xunxin.vo:javabean实体对象

注意事项：
1、部署项目到本地tomcat时，选择部署xunxin.web项目，需要配置maven依赖和项目依赖:右键--properties--Deployment Assembly--Add--Project/Java Bulid Path Entries(maven)

