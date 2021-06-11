package com.group.bookloan.admin;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.AbstractTableModel;

import com.group.bookloan.detail.DetailMain;
import com.group.bookloan.main.AppMain;
import com.group.bookloan.main.Page;
import com.group.bookloan.product.SearchModel;
import com.group.bookloan.user.MainPage;

public class SearchUser extends Page {
	JPanel b_center;
	JPanel b_south;
	Choice b_category;
	JTextField b_keyword;
	JButton search_bt;
	JTable userList;
	JScrollPane scroll_table;
	MainPage mainPage;
	AppMain appMain;
	SearchModel model;
	JButton bt_list;
	String filename;
	String product_name;
	String[] columns= {"user_id","u_id","u_name","u_phone","u_address","u_regdate","overdue"};
	String[][] records= {};
	
	//생성
	public SearchUser(AppMain appMain) {
		super(appMain);
		b_center = new JPanel();
		b_category = new Choice();
		
		b_category.add("==Choice==");
		//b_category.add("Subcategory_id");
		b_category.add("u_name");
		b_category.add("u_phone");
		b_category.add("u_address");
		b_category.add("u_regdate");
		
		b_keyword = new JTextField();
		search_bt = new JButton("찾기");
		userList = new JTable(new AbstractTableModel() {
			public int getRowCount() {
				return records.length;
			}
			public int getColumnCount() {
				return columns.length;
			}
			public String getColumnName(int col) {
				return columns[col];
			}
			public Object getValueAt(int row, int col) {
				return records[row][col];
			}
			
		}); //model = new SearchModel()
		bt_list = new JButton("목록");
		
		scroll_table = new JScrollPane(userList);
		
		b_south = new JPanel();
		
		setLayout(new FlowLayout());
		b_category.setPreferredSize(new Dimension(120,30));
		b_keyword.setPreferredSize(new Dimension(445,30));
		scroll_table.setPreferredSize(new Dimension(700,550));
		
		//이벤트
		
		b_center.add(b_category);
		b_center.add(b_keyword);
		b_center.add(search_bt);
		b_center.add(bt_list);

		b_south.add(scroll_table);
		
		add(b_center, BorderLayout.CENTER);
		add(b_south, BorderLayout.SOUTH);
		
		//getTopList();
		getList();
		//getProductList();
		
		//검색 버튼
		search_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(b_category.getSelectedIndex()==0 && b_keyword.getText().length()==0) {
					getList();
				}else {
					getListBySearch();					
				}
				//userList.updateUI();
			}
		});
		
		bt_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getList();
				//userList.updateUI();
			}
		});
		//테이블과 리스너 연결
		userList.addMouseListener(new MouseAdapter () {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
	              //   System.out.println("Mouse Double Click!");
	                 int row = userList.getSelectedRow();
	                 Object value = userList.getValueAt(row, 4);
	                 String address = ((String)value);
	                 value = userList.getValueAt(row, 2);
	                 String name = (String)value;
	                // getFilename(product_id);
	                 new MailSendApp(address,name);
	          }
			}
		});
		//보여주기
		setBounds(600,100,1200,700);
		
		//getList();
		userList.updateUI();
	}
//	public void getFilename(int n) {
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		String sql = "select filename, product_name from product where product_id = "+n;
//		try {
//			pstmt = this.getAppMain().getCon().prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			while(rs.next()) {
//				filename = rs.getString("filename");
//				product_name = rs.getString("product_name");
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	public void getList() {
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		ResultSetMetaData meta;
		model = new SearchModel();
		
		//"product_id","sub_name","product_name","author","detail","brand","canloan"
		String sql="select * from userdata";
		
		try {
			pstmt=this.getAppMain().getCon().prepareStatement(sql
					, ResultSet.TYPE_SCROLL_INSENSITIVE
					, ResultSet.CONCUR_READ_ONLY);
			
			rs=pstmt.executeQuery();
			meta = rs.getMetaData();
			
			int col_count = meta.getColumnCount();
			for (int i = 1; i <= col_count; i++) {
				String name=meta.getColumnName(i);
				model.setColumn(name); 
			}
			
			rs.last();
			int total = rs.getRow();
			
			records=new String[total][columns.length];
			
			rs.beforeFirst();
			int index=0;
			while(rs.next()) {
				records[index][0]=Integer.toString(rs.getInt("user_id"));//책번호
				records[index][1]=rs.getString("u_id");//장르
				records[index][2]=rs.getString("u_name");//책 제목
				records[index][3]=rs.getString("u_phone");//작가
				records[index][4]=rs.getString("u_address");//줄거리
				records[index][5]=rs.getString("u_regdate");//출판사
				records[index][6]=Boolean.toString(rs.getBoolean("overdue"));//대여가능
				index++;
			}
			userList.updateUI();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.getAppMain().release(pstmt, rs);
		}
	}
	
	public void release(PreparedStatement pstmt, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//	public void getTopList() {
//		PreparedStatement pstmt=null;
//		ResultSet rs = null;
//		String sql = "select * from topcategory";
//		
//		try {
//			pstmt=this.getAppMain().getCon().prepareStatement(sql);
//			rs=pstmt.executeQuery();
//			
//			while(rs.next()) {
//				b_category.add(rs.getString("top_name"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			getAppMain().release(pstmt,rs);
//		}
//	}
	
//	public void getProductList() {
//		PreparedStatement pstmt=null;
//		ResultSet rs = null;
//		ResultSetMetaData meta;
//		model = new SearchModel();
//		
//		//"product_id","sub_name","product_name","author","detail","brand","canloan"
//		String sql="select product_id, sub_name, product_name, author, detail, brand, canloan";
//				sql+=" from subcategory s, product p";
//				sql+=" where s.subcategory_id=p.subcategory_id";
//
//		try {
//			pstmt=this.getAppMain().getCon().prepareStatement(sql
//					, ResultSet.TYPE_SCROLL_INSENSITIVE
//					, ResultSet.CONCUR_READ_ONLY);
//			
//			rs=pstmt.executeQuery();
//			meta = rs.getMetaData();
//			
//			int col_count = meta.getColumnCount();
//			for (int i = 1; i <= col_count; i++) {
//				String name=meta.getColumnName(i);
//				model.setColumn(name); 
//			}
//			
//			rs.last();
//			int total = rs.getRow();
//			
//			records=new String[total][columns.length];
//			
//			rs.beforeFirst();
//			int index=0;
//			while(rs.next()) {
//				records[index][0]=Integer.toString(rs.getInt("product_id"));//책번호
//				records[index][1]=rs.getString("sub_name");//장르
//				records[index][2]=rs.getString("product_name");//책 제목
//				records[index][3]=rs.getString("author");//작가
//				records[index][4]=rs.getString("detail");//줄거리
//				records[index][5]=rs.getString("brand");//출판사
//				records[index][6]=Boolean.toString(rs.getBoolean("canloan"));//대여가능
//				index++;
//			}
//			userList.updateUI();			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//			this.getAppMain().release(pstmt, rs);
//		}
//	}
	
	
	public void getListBySearch() {
		String category = b_category.getSelectedItem().toString();
		String keyword = b_keyword.getText();
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		ResultSetMetaData meta;
		model = new SearchModel();
		
		String sql="select * from userdata where "+category+" like '%"+keyword+"%' ";
		
		try {
			pstmt=this.getAppMain().getCon().prepareStatement(sql
					, ResultSet.TYPE_SCROLL_INSENSITIVE
					, ResultSet.CONCUR_READ_ONLY);
			
			rs=pstmt.executeQuery();
			meta = rs.getMetaData();
			
			int col_count = meta.getColumnCount();
			for (int i = 1; i <= col_count; i++) {
				String name=meta.getColumnName(i);
				model.setColumn(name); 
			}
			
			rs.last();
			int total = rs.getRow();
			
			records=new String[total][columns.length];
			
			rs.beforeFirst();
			int index=0;
			while(rs.next()) {
				records[index][0]=Integer.toString(rs.getInt("user_id"));//책번호
				records[index][1]=rs.getString("u_id");//장르
				records[index][2]=rs.getString("u_name");//책 제목
				records[index][3]=rs.getString("u_phone");//작가
				records[index][4]=rs.getString("u_address");//줄거리
				records[index][5]=rs.getString("u_regdate");//출판사
				records[index][6]=Boolean.toString(rs.getBoolean("overdue"));//대여가능
				index++;
			}
			userList.updateUI();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			this.getAppMain().release(pstmt, rs);
		}
		
	}
	
}
