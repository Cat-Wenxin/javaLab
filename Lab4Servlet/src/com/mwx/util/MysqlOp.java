package com.mwx.util;


import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mwx.dao.UserDao;
import com.mwx.entity.Users;
/*import com.mwx.dao.PersonDao;
import com.mwx.dao.UserDao;
import com.mwx.entity.Persons;
import com.mwx.entity.Users;*/

public class MysqlOp {
	
	private Connection con = null;
	
	public MysqlOp() throws Exception {
		
		// TODO Auto-generated constructor stub
		con=getCon();
	}
	
	public Connection getCon()throws Exception{
		Properties properties = new Properties();
        InputStream in = MysqlOp.class.getResourceAsStream("config.properties");
        properties.load(in);  
        DataSource ds = DruidDataSourceFactory.createDataSource(properties);  
        Connection conn = ds.getConnection(); 
        in.close(); 
		return conn;
	} 
	
	 
	public void CloseCon()throws Exception{
		if(this.con!=null) { 
			this.con.close();
			System.out.println("已断开与数据库的连接。");
		}
	}
	
	
	
	public void connect() {
		MysqlOp jdncConn;
		try {
			jdncConn = new MysqlOp();
			jdncConn.getCon();
			System.out.println("数据库连接成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("数据库连接失败！");
			e.printStackTrace();
		}
	}
	
/*	public static void main(String[] args) throws Exception {
		MysqlOp jc=new MysqlOp();
		jc.connect();
		Users user=new Users("lily","123456","123456789");
		UserDao userdao=new UserDao(jc);
		userdao.add(user);
	  	userdao.createTable();
		userdao.add(user);
		userdao.display();
		Persons person=new Persons("温馨","毛",18,"1008611");
		PersonDao persondao=new PersonDao(jc);
		
		persondao.createTable();
		persondao.add(person);
		persondao.display();
		userdao.display();
		persondao.drop();
		userdao.drop();
		jc.CloseCon();
		
	} */
	
	
}
