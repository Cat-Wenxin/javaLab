package com.mwx.entity;

import java.util.List;

public class PersonJsonBean {
	private int flag;
	private String message;
	private List<Persons> perosns;
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
	public List<Persons> getPerosn() {
		return perosns;
	}
	public void setPerosn(List<Persons> perosn) {
		this.perosns = perosn;
	}

}
