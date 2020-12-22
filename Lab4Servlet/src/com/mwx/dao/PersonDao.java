package com.mwx.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mwx.entity.Persons;
import com.mwx.util.MysqlOp;

public class PersonDao {
	private MysqlOp con;
	
	public PersonDao(MysqlOp con) {
		super();
		this.con = con;
	}
	
	public boolean createTable() throws Exception {
        String sql ="create table persons("
				+"username varchar(10) not null,"
				+"name varchar(20) not null,"
				+"age int,"
				+"teleno char(11)," 
				+"primary key ( name )"
				+")";  
        Statement state=con.getCon().createStatement();
        state.executeUpdate(sql);
        state.close();
        System.out.println("创建persons表成功！");
        return true;
    } 
	
	public boolean delete(String mark,int  pattern) throws SQLException, Exception {
		Statement state=con.getCon().createStatement();
		if(pattern==1) {
			String sql = "delete from persons where username like '"+mark+"%'";//鍒犻櫎 
	        state.executeUpdate(sql);
		}
		if(pattern==2) {
			String sql="delete from persons where username = '"+mark+"'";
			state.executeUpdate(sql);
		}
		if(pattern==3) {
			String sql="delete from persons where name = '"+mark+"'";
			state.executeUpdate(sql);
		}
        state.close();
		return true;
	}
	
	public int add(Persons person) throws SQLException, Exception {
		Statement state=con.getCon().createStatement();
		//person的信息
		String param="'"+person.getUsername()+"','"+person.getName()+"'";
		if(person.getAge()!=null) {
			param+=",'"+person.getAge()+"'";
		}
		if(person.getTeleno()!="") {
			param+=",'"+person.getTeleno()+"'";
		}
		//sql语句的信息
		String sql="insert into persons(username,name";
		if(person.getAge()!=null&&person.getTeleno()!="") {
			sql+=",age,teleno)";
		}
		else if(person.getAge()==null&&person.getTeleno()=="") {
			sql+=")";
		}
		else if(person.getAge()!=null&&person.getTeleno()=="") {
			sql+=",age)";
		}
		else {
			sql+=",teleno)";
		}
		sql+=" values ("+param+")";
	//	state.executeUpdate(sql);
		
		int flag=0;//用于判断是哪一种插入情况
		int panduan=-1;
		
		//查找persons表中是否已经又某个username
		String sql2 ="select * from persons where name ='"+person.getName()+"';";
		ResultSet rs=state.executeQuery(sql2);
		if(!rs.next()) {//如果没有则直接插入
			panduan=state.executeUpdate(sql);
			System.out.println("插入成功！");
			flag++; 
		}else {
			this.delete(person.getName(), 3);
			panduan=state.executeUpdate(sql);
			System.out.println("插入并更新数据成功！");
			flag--;
		}
		
		//查找user表中是否存在该username
		String temp ="select * from users where username ='"+person.getUsername()+"';";
		rs=state.executeQuery(temp);
		if(!rs.next()) {//不存在则创建一个
			panduan=state.executeUpdate("insert into users(username,pass) values ('"+person.getUsername()+"','888888')");
			System.out.println("插入person和user成功！");
			flag+=100;
		}
		
		rs.close();
		state.close();
		return flag;
		//若为1，则username存在user但不存在person；若为-1，则存在于person和user；若为101，则不存在person和user；若为99，则存在person，不存在user。
	}
	
	public void display() throws SQLException, Exception {
		Statement state =con.getCon().createStatement();
		state.executeUpdate("select ifnull(age,'') from persons;");
		ResultSet rs=state.getResultSet();
		String persons_head="*****************表persons***************";
		String persons_title="|username   name    age      teleno   |";
		System.out.println(persons_head);
		System.out.println(persons_title);
		String query="select * from persons";
		rs=state.executeQuery(query);
		while(rs.next()) {
			String s1=rs.getString(1);
			String s2=rs.getString(2);
			String s3=rs.getString(3);
			String s4=rs.getString(4);
			System.out.println("| "+s1+"  "+s2+"   "+s3+"     "+s4+"     |");
		}
		rs.close();
		state.close();
	}
	
	public List <Persons> showpersons(String username) throws SQLException, Exception {
		List <Persons> persons =new ArrayList();
		Statement state =con.getCon().createStatement();
		ResultSet rs=state.getResultSet();
		String query="select * from persons where username ='"+username+"';";
		rs=state.executeQuery(query);
		while(rs.next()) {
			String name=rs.getString(2);
			String age1=rs.getString(3);
			System.out.println("name="+name+",age1="+age1);
			Integer age=null;
			if(age1!=null) 
				age = Integer.valueOf(age1);
			else age = null;
			String teleno=rs.getString(4);
			
			Persons perosn = new Persons(username,name,age,teleno);
			persons.add(perosn);
		}
		rs.close();
		state.close();
		return persons;
	}
	
	public boolean drop() throws SQLException, Exception {
		Statement state =con.getCon().createStatement();
		String sql="drop table persons;";
		state.executeUpdate(sql);
		state.close();
		System.out.println("已删除person表。");
		return true;
	}
}
