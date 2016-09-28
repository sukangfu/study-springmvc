<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>时间转换器测试</title>
<script type="text/javascript" src="${ctx}/plugin/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctx}/plugin/datePicker/WdatePicker.js"></script>
</head>
<body>
	
	<form action="${ctx }/format/date2.htm" method="post">
		<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate" name="user.birth" 
		value="<fmt:formatDate value="${user.birth }" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" /> 
		<input type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate" name="date"
		value="<fmt:formatDate value="${date }" pattern="yyyy-MM-dd"/>" readonly="readonly" />
		<input type="text" name="pet.petName" value="${pet.petName }" />
		<button type="submit">提交</button>
	</form>
	
</body>
</html>