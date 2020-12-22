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
import com.mwx.util.MysqlOp;


@WebServlet("/ChangePass")
public class ChangePass extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out =response.getWriter();//�����,��ͻ������
        //��ȡ���Կͻ��˵�����
        String username=request.getParameter("username").trim();//trim()Ϊɾ���ַ�����ͷβ�հ׷�
        String oldpass=request.getParameter("oldpass").trim();
        String newpass=request.getParameter("newpass").trim();
        System.out.println(username);
        System.out.println(oldpass); 
        System.out.println(newpass); 
  
        
		//�������ݿ�
		MysqlOp conn;
		try {
			conn = new MysqlOp();
			GsonBean gsonbean=new GsonBean();//���ڸ��ͻ��˻ش�Gson����
			UserDao ud = new UserDao(conn);
			String pass=ud.getpass(username);
			if(oldpass.equals(pass)) {
				if(ud.changepass(username, newpass)) {
					gsonbean.setFlag(1);
					gsonbean.setData(null);
					gsonbean.setMessage("�޸ĳɹ��������µ�¼");
				}else {
					gsonbean.setFlag(-1);
					gsonbean.setData(null);
					gsonbean.setMessage("�޸�ʧ�ܣ����ݿ����������");
				}
			}else {
				gsonbean.setFlag(0);
				gsonbean.setData(null);
				gsonbean.setMessage("�޸�ʧ�ܣ����������");
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
