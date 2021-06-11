package com.group.bookloan.user;

public class User {
	private int user_id;
	private String u_id;
	private String u_pass;
	private String u_name;
	private String u_phone;
	private String u_regdate;
	private Boolean overdue;
	private String u_address;
	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getU_pass() {
		return u_pass;
	}
	public void setU_pass(String u_pass) {
		this.u_pass = u_pass;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_phone() {
		return u_phone;
	}
	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}
	public String getU_regdate() {
		return u_regdate;
	}
	public void setU_regdate(String u_regdate) {
		this.u_regdate = u_regdate;
	}
	public Boolean getOverdue() {
		return overdue;
	}
	public void setOverdue(Boolean overdue) {
		this.overdue = overdue;
	}
	public String getU_address() {
		return u_address;
	}
	public void setU_address(String u_address) {
		this.u_address = u_address;
	}
}
