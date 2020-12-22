package com.mwx.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mwx.dao.PersonDao;
import com.mwx.entity.Persons;
import com.mwx.util.MysqlOp;


@WebServlet("/Addperson")
public class Addperson extends HttpServlet{
	
	public Addperson() {
        super();
        // TODO Auto-generated constructor stub
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		ServletContext context = this.getServletContext();   
		String username = new String(request.getParameter("username").getBytes("iso-8859-1"),"UTF-8");
		String name = new String(request.getParameter("name").getBytes("iso-8859-1"),"UTF-8");
		String age1 = request.getParameter("age");
		Integer age;
		if(age1.length()>0) age = Integer.valueOf(age1);
		else age = null;
		String teleno = request.getParameter("teleno");
		MysqlOp conn;
		try {
			conn = new MysqlOp();
			PersonDao po = new PersonDao(conn);
			int flag = 0;
			try { 
				flag = po.add(new Persons(username,name,age,teleno));
				System.out.println(flag);
				context.setAttribute("username1",username);
				context.setAttribute("flag1",flag);
				response.sendRedirect("check-add.jsp");
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
		
		doGet(request, response);
	}

}
