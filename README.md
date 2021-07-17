# 软工3代码

## 项目部署地址

项目地址：http://47.118.57.5/

Jenkins：http://47.118.57.5:8081/

Tomcat:  http://47.118.57.5:8080/

## 项目成果展示视频

迭代一：https://www.bilibili.com/video/BV1dv411b77o/

## 项目结构

### 数据库

存放在sql中，用mysql运行sql文件

### 前端

放在web文件夹中

### 后端

#### service

业务层接口

#### serviceImpl

业务层实现

#### controller

控制层，与前端交流

#### data

数据层接口

#### dataImpl

数据层接口实现，在xml中用数据库语言与mysql交互

#### po

数据持久层，存访从数据库获取的数据