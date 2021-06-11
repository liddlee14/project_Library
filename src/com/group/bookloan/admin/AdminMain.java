package com.group.bookloan.admin;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.group.bookloan.main.AppMain;
import com.group.bookloan.main.Page;
import com.group.bookloan.product.Product;
import com.group.bookloan.product.ProductModel;

public class AdminMain extends Page{
	JPanel p_north;
	JPanel p_east;
	JPanel p_west;
	Choice s_choice;
	JTextField s_text;
	JButton s_bt;
	JTable table;
	JScrollPane scroll;
	AppMain appMain;
	ProductModel model;
	private int product_id;
	UpdateForm up;

	public AdminMain(AppMain appMain) {
		super(appMain);
		//생성
		p_north = new JPanel();
		p_east = new JPanel();
		p_west = new JPanel();
		s_choice = new Choice();
		s_text = new JTextField(32);
		s_bt = new JButton("검색");
		table = new JTable();
		scroll = new JScrollPane(table);
		
		
		s_choice.add("Title");
		s_choice.add("user");
		
		p_north.add(s_choice);
		p_north.add(s_text);
		p_north.add(s_bt);
		add(p_north,BorderLayout.NORTH);
		
		p_east.add(scroll);
		add(p_east,BorderLayout.EAST);
		//add(p_west,BorderLayout.WEST);
		//디자인
		scroll.setPreferredSize(new Dimension(750,550));
		//p_west.setPreferredSize(new Dimension(250,550));
		//p_west.setBackground(Color.cyan);
		
		getList();
		table.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				
				int row = table.getSelectedRow();
				Object value = table.getValueAt(row, 0);
				setProduct_id(Integer.parseInt((String)value));
				//System.out.println(getProduct_id());
				getAppMain().showHide(10);
				
			}
		});
		//System.out.println(table.getSelectedRow());
	}
	
	public void getList() {
		table.removeAll();
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		sb.append("product_id, product_name, deadline, canloan, canextend, u_name, u_phone, u_address ");
		sb.append("from product as p left outer join userdata as u ");
		sb.append("on p.user_id = u.user_id ");
		sb.append("order by product_id desc");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData meta;
		model = new ProductModel();
		
		try {
			pstmt = this.getAppMain().getCon().prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			meta = rs.getMetaData();
			
			//컬럼의 수, 이름 적용..
			int col_count = meta.getColumnCount();
			for(int i = 1; i<=col_count; i++) {
				String name = meta.getColumnName(i);
				model.setColumn(name);
			}
			while(rs.next()) {
				Product product = new Product();
				product.setProduct_id(rs.getInt("product_id"));
				product.setProduct_name(rs.getString("product_name"));
				product.setDeadline(rs.getInt("deadline"));
				product.setCanloan(rs.getBoolean("canloan"));
				product.setCanextend(rs.getBoolean("canextend"));
				product.setU_name(rs.getString("u_name"));
				product.setU_phone(rs.getString("u_phone"));
				product.setU_address(rs.getString("u_address"));
				
				model.setData(product);
			}
			table.setModel(model);
			
			table.updateUI();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
}
