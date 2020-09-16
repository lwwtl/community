## 个人社区

## 资料
[Spring文档](https://spring.io/)  
[Bootstrap组件](https://v3.bootcss.com/components/#navbar)  
[Github Oauth](https://developer.github.com/apps/)  
[Thymeleaf](https://www.thymeleaf.org/index.html) 
[Ufile-sdk](https://github.com/ucloud/ufile-sdk-java)   
[Markdown插件]  
## 工具
[Git](https://github.com/)
[Flyway]  
[Postman]  
## Instruction
git status  查看状态  
git add .   把当前目录改变的文件存入暂存  
git commit -m "注释"    
git status  查看工作区  
git push    提交  
## 脚本
create table USER  
(  
	ID INT auto_increment,  
	ACCOUNT_ID VARCHAR(100),  
	NAME VARCHAR(50),  
	TOKEN CHAR(36),  
	GMT_CREATE BIGINT,  
	GMT_MODIFIED BIGINT,  
	constraint USER_PK  
	primary key (ID)  
);  
create table QUESTION
(
    ID            INT auto_increment,
    TITLE         VARCHAR(50),
    DESCRIPTION   TEXT,
    GMT_CREATE    BIGINT,
    GMT_MODIFIED  BIGINT,
    CREATOR       INT,
    COMMENT_COUNT INT default 0,
    VIEW_COUNT    INT default 0,
    LIKE_COUNT    INT default 0,
    TAG           VARCHAR(256),
    constraint QUESTION_PK
        primary key (ID)
);
```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
## 部署
yum update  
yum install git 安装git  
pwd 显示工作路径  
mkdir App   创建一个叫做APP目录  
ls  查看目录中的文件  
cd App  进入目录  
git clone  
cd community  
yum install maven 安装maven  
mvn compile package  
more src/main/resources/application.properties 以页形式查看文件内容  
cp file1 file2 复制一个文件  
vim src/main/resources/application.properties 编辑  
a 开始编辑  
esc 退出到命令行  
:wq 保存退出 
mvn package 导新配置  
java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar  
ps -aux|grep java  杀进程  
git pull  
