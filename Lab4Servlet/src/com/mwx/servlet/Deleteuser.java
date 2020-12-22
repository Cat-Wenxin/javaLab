package com.mwx.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mwx.dao.PersonDao;
import com.mwx.dao.UserDao;
import com.mwx.entity.Persons;
import com.mwx.util.MysqlOp;


@WebServlet("/Deleteuser")
public class Deleteuser extends HttpServlet {
	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		ServletContext context = this.getServletContext();   
		String username = request.getParameter("username");
		response.getWriter().append("Served at: ").append(request.getContextPath());
		MysqlOp conn;
		try {
			System.out.println("进入delete");
			conn = new MysqlOp();
			UserDao uo = new UserDao(conn);
			int flag = -1;
			try { 
				flag = uo.delete(username, 2);
				System.out.println(flag);
				context.setAttribute("username2", username);//保存上下文
				context.setAttribute("flag2", flag);//保存上下文
		        response.sendRedirect("check-delete.jsp");//重定向
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.CloseCon();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
