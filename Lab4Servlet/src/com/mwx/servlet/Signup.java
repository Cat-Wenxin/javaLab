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




@WebServlet("/Signup")
public class Signup extends HttpServlet {
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out =response.getWriter();//�����,��ͻ������
        //��ȡ���Կͻ��˵�����
        String username=request.getParameter("username").trim();//trim()Ϊɾ���ַ�����ͷβ�հ׷�
        String password=request.getParameter("password").trim();
        String teleno=request.getParameter("teleno").trim(); 
        
        System.out.println(username);
        System.out.println(password);
        System.out.println(teleno);  
        
     /*   String username="Test5";//trim()Ϊɾ���ַ�����ͷβ�հ׷�
        String password="555555";
        String teleno="18570779300"; */
        
		//�������ݿ�
		MysqlOp conn;
		try {
			conn = new MysqlOp();
			GsonBean gsonbean=new GsonBean();//���ڸ��ͻ��˻ش�Gson����
			Users user = new Users(username,password,teleno);
			UserDao ud = new UserDao(conn);
			if(!ud.hasusername(username)) {
				System.out.println("name"+ud.hasusername(username));
				if(!ud.hasteleno(teleno)) {
					System.out.println("teleno"+ud.hasteleno(teleno));
					ud.add(user);
					gsonbean.setFlag(1);
					gsonbean.setData(user);
					gsonbean.setMessage("ע��ɹ������¼");
				}else {
					System.out.println("teleno"+ud.hasteleno(teleno));
					gsonbean.setFlag(0);
					gsonbean.setData(user);
					gsonbean.setMessage("ע��ʧ��,�ú�����ע���");
				}
			}else {
				gsonbean.setFlag(-1);
				gsonbean.setData(user);
				gsonbean.setMessage("ע��ʧ��,�û����Ѵ���");
				System.out.println("ע��ʧ��,�û����Ѵ���");
			}
			conn.CloseCon();
			Gson gson =new Gson();
			String json=gson.toJson(gsonbean);//��json���ݴ����ͻ���
		//	out.println(json);
			out.write(json.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			response.getWriter().close();//�ر������
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
