<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "com.mwx.util.*" %>
<%@ page import = "com.mwx.servlet.*" %>
<%@ page import = "java.sql.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
		<link rel="stylesheet" type="text/css" href="css/demo.css" />
        <link rel="stylesheet" type="text/css" href="css/style.css" />
		<script type="text/javascript" src="js/modernizr.custom.04022.js"></script>
		<title>数据库表</title>
	</head>
	<body>
		<% 
			MysqlOp conn = new MysqlOp();
		
		%>
		<div class="container" >
			<section class="af-wrapper">
				<h1 style="text-align:center;">数据表user信息</h1>
				<table class="customers">
					<tr>
						<th >username</th>
						<th >password</th>
						<th>teleno</th>
					</tr>
				
				<%
					String sql = "select * from users";
					Statement state =conn.getCon().createStatement();
					ResultSet rs = state.executeQuery(sql);
					while(rs.next()) {
						String username = rs.getString("username");
				   	 	String password = rs.getString("pass");
				   	 	String teleno =rs.getString("teleno");
				%>
				    	<tr class="alt">
				    		<td><%= username %></td>
				    		<td><%= password %></td>
				    		<td><%=teleno %></td>
				    	</tr>
				<%
					}
					rs.close();
					state.close();
				%>	
				</table>
			</section>
			<section class="af-wrapper">
				<h1 style="text-align:center;">数据表person信息</h1>
				<table class="customers">
					<tr >
						<th >username</th>
						<th >name</th>
						<th >age</th>
						<th >teleno</th>
					</tr>
				
				<%
					String sql1 = "select * from persons";
					Statement state1 =conn.getCon().createStatement();
					ResultSet rs1 = state1.executeQuery(sql1);
					while(rs1.next()) {
						String username = rs1.getString("username");
					    String name = rs1.getString("name");
					    Integer age = rs1.getInt("age");
					    int flag=0;
					    String str="";
					    if(age==0) flag=1;
					    String teleno = (rs1.getString("teleno")==null?"":rs1.getString("teleno"));
					    
					   // if(teleno.length()==0) teleno = null;		
				%>
				    	<tr class="alt">
				    		<td><%= username %></td>
				    		<td><%= name %></td>
				    		<td>
				    			<%if(flag==1) out.println(str);
				    			  else out.println(age);
				    			%>
				    		</td>
				    		<td><%= teleno %></td>
				    	</tr>
				<%
					}
					rs1.close();
					state1.close();
					conn.CloseCon();
				%>	
				</table> 
			</section>
			<a href = "index.jsp" style="font-size:18pt;position: absolute;left: 43%;">返回数据库操作</a>
		</div>
	</body>
</html>