package com.mwx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mwx.entity.Users;
import com.mwx.util.MysqlOp;

public class UserDao {
	private MysqlOp con;
	
	
	public UserDao(MysqlOp con) {
		super();
		this.con = con;
	}
 

	public boolean createTable() throws Exception {
        String sql = "create table users"
                + "( "
                + "username varchar(10) not null,"
                + "pass varchar(8) not null,"
                + "teleno char(11),"
                + "primary key (username)"
                + ")"; 
        Statement state=con.getCon().createStatement();
        state.executeUpdate(sql);
        state.close();
        System.out.println("创建user表成功！");
        return true;
    }
	
	public int delete(String mark,int  pattern) throws SQLException, Exception {
		Statement state=con.getCon().createStatement();
		if(pattern==1) {
			String sql = "delete from users where username like '"+mark+"%'";
	        state.executeUpdate(sql);
		}
		int flag=-1;
		if(pattern==2) {
			String sql="delete from users where username = '"+mark+"'";
			flag=state.executeUpdate(sql);
			if(flag>0) {
				String sql0="delete from persons where username = '"+mark+"'";
				state.executeUpdate(sql0);
				System.out.println("删除"+mark+"成功！");
			}
			else
				System.out.println("不存在，删除"+mark+"失败！");
			
		}
        state.close();
		return flag;
	}
	
	public int add(Users user) throws SQLException, Exception {
		int flag=-1;
		Statement state=con.getCon().createStatement();
		String sql="insert into users(username,pass,teleno) values ('"+user.getUsername()+"','"+user.getPass()+"','"+user.getTeleno()+"')";
		flag=state.executeUpdate(sql);
		if(flag>0)
			System.out.println("添加"+user.getUsername()+"成功！");
		else
			System.out.println("添加"+user.getUsername()+"失败！");
		state.close();
		return flag;
	}
	
	public void display() throws SQLException, Exception {
		Statement state =con.getCon().createStatement();
		String users_head="*****************表users***************";
		String users_title="|username             pass|";
		System.out.println(users_head);
		System.out.println(users_title);
		String query="select * from users";
		ResultSet rs=state.executeQuery(query);
		while(rs.next()) {
			String s1=rs.getString(1);
			String s2=rs.getString(2);
			System.out.println("|        "+s1+"                 "+s2+"     |");
		//	System.out.println(" ");
		}
		rs.close();
		state.close();
	}
	
	public String getpass(String username) throws SQLException, Exception {
		String sql="select pass from users where username = '"+username+"'";
		Statement state =con.getCon().createStatement();
		ResultSet rs=state.executeQuery(sql);
		String pass="";
		while(rs.next()){
			pass = rs.getString(1);  
		}
		System.out.println(pass);
		return pass;
	}
	
	public String getteleno(String username) throws SQLException, Exception {
		String sql="select teleno from users where username = '"+username+"'";
		Statement state =con.getCon().createStatement();
		ResultSet rs=state.executeQuery(sql);
		String teleno="";
		while(rs.next()){
			teleno = rs.getString(1);  
		}
		System.out.println(teleno);
		return teleno;
	}
	
	public boolean hasusername(String username) throws SQLException, Exception {
		Statement state=con.getCon().createStatement();
		String temp ="select * from users where username ='"+username+"';";
		ResultSet rs=state.executeQuery(temp);
		if(!rs.next()) {//不存在
			rs.close();
			state.close();
			return false;
		}
		rs.close();
		state.close();
		return true;
	}
	
	public boolean hasteleno(String teleno) throws SQLException, Exception {
		Statement state=con.getCon().createStatement();
		String temp ="select * from users where teleno ='"+teleno+"';";
		ResultSet rs=state.executeQuery(temp);
		if(!rs.next()) {//不存在
			rs.close();
			state.close();
			return false;
		}
		rs.close();
		state.close();
		return true;
	}
	
	public boolean changepass(String username,String password) throws SQLException, Exception {
		Statement state=con.getCon().createStatement();
		String temp ="update users set pass='"+password+"' where username ='"+username+"';";
		boolean isok =false;
		if(state.executeUpdate(temp)>0) isok=true;
		state.close();
		return isok;
	}
	
	public boolean drop() throws SQLException, Exception {
		Statement state =con.getCon().createStatement();
		String sql="drop table users;";
		state.executeUpdate(sql);
		state.close();
		System.out.println("已删除users表。");
		return true;
	}
}
