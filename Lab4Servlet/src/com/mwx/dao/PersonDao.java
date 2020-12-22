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
        System.out.println("����persons��ɹ���");
        return true;
    } 
	
	public boolean delete(String mark,int  pattern) throws SQLException, Exception {
		Statement state=con.getCon().createStatement();
		if(pattern==1) {
			String sql = "delete from persons where username like '"+mark+"%'";//删除 
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
		//person����Ϣ
		String param="'"+person.getUsername()+"','"+person.getName()+"'";
		if(person.getAge()!=null) {
			param+=",'"+person.getAge()+"'";
		}
		if(person.getTeleno()!="") {
			param+=",'"+person.getTeleno()+"'";
		}
		//sql������Ϣ
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
		
		int flag=0;//�����ж�����һ�ֲ������
		int panduan=-1;
		
		//����persons�����Ƿ��Ѿ���ĳ��username
		String sql2 ="select * from persons where name ='"+person.getName()+"';";
		ResultSet rs=state.executeQuery(sql2);
		if(!rs.next()) {//���û����ֱ�Ӳ���
			panduan=state.executeUpdate(sql);
			System.out.println("����ɹ���");
			flag++; 
		}else {
			this.delete(person.getName(), 3);
			panduan=state.executeUpdate(sql);
			System.out.println("���벢�������ݳɹ���");
			flag--;
		}
		
		//����user�����Ƿ���ڸ�username
		String temp ="select * from users where username ='"+person.getUsername()+"';";
		rs=state.executeQuery(temp);
		if(!rs.next()) {//�������򴴽�һ��
			panduan=state.executeUpdate("insert into users(username,pass) values ('"+person.getUsername()+"','888888')");
			System.out.println("����person��user�ɹ���");
			flag+=100;
		}
		
		rs.close();
		state.close();
		return flag;
		//��Ϊ1����username����user��������person����Ϊ-1���������person��user����Ϊ101���򲻴���person��user����Ϊ99�������person��������user��
	}
	
	public void display() throws SQLException, Exception {
		Statement state =con.getCon().createStatement();
		state.executeUpdate("select ifnull(age,'') from persons;");
		ResultSet rs=state.getResultSet();
		String persons_head="*****************��persons***************";
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
		System.out.println("��ɾ��person��");
		return true;
	}
}
