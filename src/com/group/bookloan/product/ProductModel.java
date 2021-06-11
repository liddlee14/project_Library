package com.group.bookloan.product;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ProductModel extends AbstractTableModel{
	Vector<Product> data = new Vector<Product>();
	Vector<String> column = new Vector<String>();
	public void setColumn(String name) {
		column.add(name);
	}
	public void setData(Product product) {
		data.add(product);
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
		Product product = data.get(row);
		
		if(col==0) {
			value = Integer.toString(product.getProduct_id());
		}else if (col==1) {
			value = product.getProduct_name();
		}else if (col==2) {
			value = Integer.toString(product.getDeadline());
		}else if (col==3) {
			value = Boolean.toString(product.isCanloan());
		}else if (col==4) {
			value = Boolean.toString(product.isCanextend());			
		}else if (col==5) {
			value = product.getU_name();
		}else if (col==6) {
			value = product.getU_phone();
		}else if (col==7) {
			value = product.getU_address();
		}
		
		return value;
	}
	
}
