<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>查看宠物详细信息</title>
</head>
<body>
	<!-- 读取Model 的数据 -->
	宠物主人：${pet.ownerId } <br/>
	宠物ID： ${pet.petId } <br />
	宠物名称： ${pet.petName } <br/>
</body>
</html>