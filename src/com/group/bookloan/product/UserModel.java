package com.group.bookloan.product;

import java.util.Vector;

public class UserModel {
	Vector<User> data = new Vector<User>();
	Vector<String> column = new Vector<String>();
	public void setColumn(String name) {
		column.add(name);
	}
	public void setData(User user) {
		data.add(user);
	}
	
	public int getRowCount() {
		
		return data.size();
	}
	public int getColumnCount() {
		return column.size();
	}
	public String getColumnName(int col) {
		return column.get(col);
	}
	
	public Object getValueAt(int row, int col) {
		String value = null;
		User user = data.get(row);
		
		if(col==0) {
			value = Integer.toString(user.getUser_id());
		}else if (col==1) {
			value = user.getU_id();
		}else if (col==2) {
			value = user.getU_name();
		}else if (col==3) {
			value = user.getU_phone();
		}else if (col==4) {
			value = user.getU_regdate();			
		}else if (col==5) {
			value = user.getU_address();
		}else if (col==6) {
			value = Boolean.toString(user.isOverdue());
		}
		
		return value;
	}
}
