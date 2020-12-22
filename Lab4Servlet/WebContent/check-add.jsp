<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>数据插入结果</title>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
		<script type="text/javascript" src="js/modernizr.custom.04022.js"></script>
	</head>
	<body>
		<div class="container">
			<section class="af-wrapper">
				<h1 style="text-align:center;">数据库操作结果</h1>
				<h2 style="text-align:center;">
					<%
						String username = (String)application.getAttribute("username1");
						Integer flag = (Integer)application.getAttribute("flag1");
						if(flag == 1) 
							out.println("已将"+username+"插入persons表中！");
						else if(flag==-1)
							out.println("已更新"+username+"在persons表中的信息！");
						else if(flag==101)
							out.println("已将"+username+"插入在persons表和users表中！");
						else if(flag==99) 
							out.println("已将"+username+"插入在uers表中，并更新了persons表中的数据！");
						else 
							out.println("插入"+username+"失败！");
					%>
				</h2>
				<a href = "showtables.jsp" style="font-size:16pt;position: relative;left: 37%;">查看数据库数据</a>
				<br>
				<a href = "index.jsp" style="font-size:16pt;position: relative;left: 37%;">返回数据库操作</a>
			</section>
		</div>
	</body>
</html>