<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>注册用户</title>
  </head>
  
  <body>
  		<!-- 
  		/user.html
  		/user/update.html
  		/user/update2.html
  		 -->
  		<form method="post" action='<c:url value="/session/handle1.html" />'>	
  			<table>
  				<tr>
  					<td>用户名：</td>
  					<td><input type="text" name="userName" /></td>
  				</tr>
  				<%--<tr>
  					<td>密  码：</td>
  					<td><input type="password" name="password" /></td>
  				</tr>
  				--%><tr>
  					<td>姓  名：</td>
  					<td><input type="text" name="realName" /></td>
  				</tr>
  				<tr>
  					<td colspan="2"><input type="submit" value="提交" /></td>
  				</tr>
  			</table>
  		</form>  	
  </body>
</html>
