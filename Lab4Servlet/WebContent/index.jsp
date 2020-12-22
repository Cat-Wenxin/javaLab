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
		<title>首页</title>
	</head>
	<body>
		<div class="container">
			<section class="af-wrapper" >
		            <h3 style="text-align:center;">数据表Person插入信息</h3>
					<form class="af-form" id="af-form"  action = "Addperson" method="post"  onsubmit="return check();">
						<div class="af-outer af-required">
							<div class="af-inner">
								<label for="input-name">username</label>
								<input id="input-name" 
									   placeholder="请输入10个以内字符" 
									   maxlength="10" 
									   type="text" 
									   name ="username" 
									   required="required"/>
							</div>
						</div>
						<div class="af-outer af-required">
							<div class="af-inner">
							  <label for="input-name">name</label>
							  <input id="input-name"
							  		 placeholder="请输入20个以内字符" 
									 maxlength="20" 
									 type="text" 
									 name ="name" 
									 required="required"/>
							</div>
						</div>
						<div class="af-outer">
							<div class="af-inner">
							  <label for="input-email">age</label>
							  <input id="input-email"
							  		 placeholder="请输入一个整数" 
									 type="text" 
									 name ="age"/>
							</div>
						</div>
						<div class="af-outer">
							<div class="af-inner">
							  <label for="input-phone">teleno</label>
							  <input id="input-phone" 
							  		 placeholder="请输入11个以内字符" 
									 type="text" 
									 maxlength="11"
									 name ="teleno"/>
							</div>
						</div>
						<input type="reset" value = "重置"/>
						<input type="submit" value="插入" /> 
					</form>
				</section>
				
				<script type="text/javascript" >
					function check()
					{
						var n = document.getElementById("input-email");
						var reg=/^(?:[1-9][0-9]?|1[01][0-9]|120)$/;
						n = n.value;
						if(n.length == 0) return true;
						if(!reg.test(n)){
							alert("age必须为1-120整数");
							return false;
						}
						return true;
					}
				
				</script>
				
				<section class="af-wrapper">
		            <h3 style="text-align:center;">数据表users删除信息</h3>
					<form class="af-form" id="af-form" action = "Deleteuser" onsubmit="return t_confirm();" method="GET">
						<div class="af-outer">
							<div class="af-inner">
							  <label for="input-phone">username</label>
							  <input id="input-phone"
									 required="required" 
									 placeholder="请输入10个以内字符" 
									 maxlength="10" 
									 type="text" 
									 name ="username" />
							</div>
						</div>
						<input type="reset" value = "重置"/>
						<input type="submit" value = "删除" />
					</form>
					<script type="text/javascript">
						function t_confirm()
						{
							var r=confirm("确认删除？");
							return r;
						}
					</script>
				</section>
				<a href = "showtables.jsp" style="font-size:18pt;position: absolute;left: 43%;">查看数据库数据</a>
        </div>	
        	
	</body>
</html>