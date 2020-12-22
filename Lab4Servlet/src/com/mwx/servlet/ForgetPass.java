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


@WebServlet("/ForgetPass")
public class ForgetPass extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out =response.getWriter();//�����,��ͻ������
        //��ȡ���Կͻ��˵�����
        String username=request.getParameter("username").trim();//trim()Ϊɾ���ַ�����ͷβ�հ׷�
        String teleno=request.getParameter("teleno").trim();
        System.out.println(username);
        System.out.println(teleno); 
   /*     String username="Test1";//trim()Ϊɾ���ַ�����ͷβ�հ׷�
        String teleno="18570779300"; */
        
		//�������ݿ�
		MysqlOp conn;
		try {
			conn = new MysqlOp();
			GsonBean gsonbean=new GsonBean();//���ڸ��ͻ��˻ش�Gson����
			UserDao ud = new UserDao(conn);
			if(!ud.hasusername(username)) {
				gsonbean.setFlag(-1);
				gsonbean.setData(null);
				gsonbean.setMessage(username+"�û���������");
			}else{
				String tele=ud.getteleno(username);
				if(teleno.equals(tele)) {
					if(ud.changepass(username, "888888")) {
						gsonbean.setFlag(1);
						gsonbean.setData(null);
						gsonbean.setMessage("����������Ϊ��888888�������µ�½");
					}else {
						gsonbean.setFlag(-2);
						gsonbean.setData(null);
						gsonbean.setMessage("�һ�����ʧ�ܣ����ݿ����");
					}
					
				}else {
					gsonbean.setFlag(0);
					gsonbean.setData(null);
					gsonbean.setMessage("�û�����绰����δ����");
				}
				
			}
			conn.CloseCon();
			Gson gson =new Gson();
			String json=gson.toJson(gsonbean);//��json���ݴ����ͻ���
			 System.out.println(json);
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
