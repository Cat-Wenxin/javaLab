package com.mwx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mwx.dao.PersonDao;
import com.mwx.dao.UserDao;
import com.mwx.entity.GsonBean;
import com.mwx.entity.Persons;
import com.mwx.entity.Users;
import com.mwx.util.MysqlOp;

@WebServlet("/ResponseAddPerson")
public class ResponseAddPerson extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out =response.getWriter();//输出流,向客户端输出
        //获取来自客户端的数据
        String username=request.getParameter("username").trim();
        String pname=request.getParameter("pname").trim();//trim()为删除字符串的头尾空白符
        String page1=request.getParameter("page").trim();
        Integer page;
		if(page1.length()>0) page = Integer.valueOf(page1);
		else page = null;
        String pteleno=request.getParameter("pteleno").trim(); 
        System.out.println(username);
        System.out.println(pname);
        System.out.println(page);
        System.out.println(pteleno);    
        
        
      //访问数据库
  		MysqlOp conn;
  		try {
  			conn = new MysqlOp();
  			GsonBean gsonbean=new GsonBean();//用于给客户端回传Gson对象
  			PersonDao pd= new PersonDao(conn);
  			Persons person =new Persons(username,pname,page,pteleno);
  			if(pd.add(person)!=0) {
  				gsonbean.setFlag(1);
				gsonbean.setData(null);
				gsonbean.setMessage("添加成功");
  			}else{
  				gsonbean.setFlag(-1);
				gsonbean.setData(null);
				gsonbean.setMessage("添加失败，请重试");
  			}
  			conn.CloseCon();
  			Gson gson =new Gson();
  			String json=gson.toJson(gsonbean);//把json数据传给客户端
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
