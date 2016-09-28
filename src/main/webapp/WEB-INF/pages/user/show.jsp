<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
  <head>
    <title>查看用户详细信息</title>
  </head>
  
  <body>
    <!-- 访问Model中的属性 -->
     用户ID：${user.userId } <br/>
  	用户名：${user.userName } <br/>
  	密码：${user.password } <br/>
  	真实姓名：${user.realName }
  </body>
</html>
