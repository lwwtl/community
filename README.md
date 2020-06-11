## 个人社区

## 资料
[Spring文档](https://spring.io/)  
[Bootstrap组件](https://v3.bootcss.com/components/#navbar)  
[Github Oauth](https://developer.github.com/apps/)  
## 工具
[Git](https://github.com/)
## Instruction
git status  查看状态  
git add .   把当前目录改变的文件存入暂存  
git status  再次查看  
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

