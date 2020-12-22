package com.mwx.entity;

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
import com.mwx.util.MysqlOp;


@WebServlet("/ShowPersons")
public class ShowPersons extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter out =response.getWriter();//�����,��ͻ������
        String username=request.getParameter("username").trim();
        
		MysqlOp conn;
  		try {
  			conn = new MysqlOp();
  			PersonJsonBean personjson=new PersonJsonBean();//���ڸ��ͻ��˻ش�Gson����
  			List <Persons> personlist = new ArrayList();
  			PersonDao pd= new PersonDao(conn);
  			personlist=pd.showpersons(username);
  			personjson.setFlag(1);
  			personjson.setMessage("�ɹ�");
  			personjson.setPerosn(personlist);
  			conn.CloseCon();
  			Gson gson =new Gson();
  			String json=gson.toJson(personjson);//��json���ݴ����ͻ���
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
