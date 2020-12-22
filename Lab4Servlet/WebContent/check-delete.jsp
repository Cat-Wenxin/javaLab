<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
		<script type="text/javascript" src="js/modernizr.custom.04022.js"></script>
		<title>数据删除结果</title>
	</head>
	<body>
		<% 
			String username = (String)application.getAttribute("username2");
			Integer flag=(Integer)application.getAttribute("flag2");
			
		%>
		<div class="container">
			<section class="af-wrapper">
				<h1 style="text-align:center;">数据库操作结果</h1>
				<h2 style="text-align:center;">
					<%
						if(flag > 0) out.println(username+"删除成功!");
						else 
							out.println("删除失败，"+username+"不存在!");
					%>
				</h2>
				<a href = "index.jsp" style="font-size:16pt;position: relative;left: 37%;">返回数据库操作</a><br>
				<a href = "showtables.jsp" style="font-size:16pt;position: relative;left: 37%;">查看数据库数据</a>
			</section>
		</div>
	</body>
</html>