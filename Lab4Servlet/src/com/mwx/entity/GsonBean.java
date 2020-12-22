package com.mwx.entity;

public class GsonBean {

	private int flag;
	private String message;
	private Object data;
	
	public GsonBean() {
		this.flag = -2;
		this.message = "";
		this.data = null;
	}
	
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
