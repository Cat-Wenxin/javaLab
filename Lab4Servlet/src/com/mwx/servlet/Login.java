package com.mwx.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mwx.dao.UserDao;
import com.mwx.entity.GsonBean;
import com.mwx.entity.Users;
import com.mwx.util.MysqlOp;

@WebServlet("/Login")
public class Login extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out =response.getWriter();//输出流,向客户端输出
        //获取来自客户端的数据
        String username=request.getParameter("username").trim();//trim()为删除字符串的头尾空白符
        String password=request.getParameter("password").trim();
        System.out.println(username);
        System.out.println(password);
        
		//访问数据库
		MysqlOp conn;
		try {
			conn = new MysqlOp();
			GsonBean gsonbean=new GsonBean();//用于给客户端回传Gson对象
			UserDao ud = new UserDao(conn);
			if(!ud.hasusername(username)) {
				gsonbean.setFlag(-1);
				gsonbean.setData(null);
				gsonbean.setMessage("登陆失败，用户名不存在");
			}else{
				String pass=ud.getpass(username);
				if(password.equals(pass)) {
					String tele= ud.getteleno(username);
					Users user = new Users(username,pass,tele);
					gsonbean.setFlag(1);
					gsonbean.setData(user);
					gsonbean.setMessage("登陆成功");
				}else {
					gsonbean.setFlag(0);
					gsonbean.setData(null);
					gsonbean.setMessage("登陆失败，密码错误");
				}
				
			}
			conn.CloseCon();
			Gson gson =new Gson();
			String json=gson.toJson(gsonbean);//把json数据传给客户端
			 System.out.println(json);
		//	out.println(json);
			out.write(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			response.getWriter().close();//关闭输出流
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
