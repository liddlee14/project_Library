package com.group.bookloan.product;

import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class SearchModel extends AbstractTableModel{
	Vector<Search> data=new Vector<Search>();
	Vector<String> column=new Vector<String>();
	public void setColumn(String name) {
		column.add(name);
	}
	public void setData(Search search) {
		data.add(search);
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
		Search search = data.get(row);
		
		if(col==0) {
			value = Integer.toString(search.getProduct_id());
		}else if(col==1) {
			value = search.getProduct_name();
		}else if(col==2) {
			value = search.getDay_end();
		}else if(col==3) {
			value = Boolean.toString(search.getCanloan());
		}else if(col==4) {
			value = Boolean.toString(search.getCanextend());
		
	}
		return value;
	}
	
}
