package com.group.bookloan.admin;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.group.bookloan.main.AppMain;
import com.group.bookloan.main.MyCanvas;
import com.group.bookloan.main.Page;

import utill.FileManager;

public class AddForm extends Page{
	JPanel p_west;
	MyCanvas can;
	JButton bt_file;
	
	JFileChooser chooser;
	Toolkit kit=Toolkit.getDefaultToolkit();
	Image image;
	Thread thread;
	
	JPanel p_center;
	JLabel b_name;
	JTextField bf_name;
	JLabel b_plot;
	JTextArea bf_plot;
	JScrollPane scroll;
	JLabel b_writer;
	JTextField bf_writer;
	JLabel b_pub;
	JTextField bf_pub;
	JLabel b_op;
	JLabel sub_b;
	Choice bf_op;
	Choice sub_bf;
	FileManager mng;
	
	JPanel p_east;
	JButton bt_add;
	JButton bt_can;

	FileInputStream fis;
	FileOutputStream fos;
	String ext;
	String time;
	AdminMain addmain;
	int sub_category;
	public AddForm(AppMain appMain) {
		//생성
		super(appMain);
		p_west = new JPanel();
		bt_file = new JButton("이미지를 넣어주세요");
		can = new MyCanvas(370,430);
		chooser = new JFileChooser("D:\\korea202102_javaworkspace\\Library\\res");
		
		p_center = new JPanel(); 
		b_name = new JLabel("책 제목");
		bf_name = new JTextField();
		b_plot = new JLabel("줄거리");
		bf_plot = new JTextArea();
		scroll = new JScrollPane(bf_plot);
		b_writer = new JLabel("작가");
		bf_writer = new JTextField();
		b_pub = new JLabel("출판사");
		bf_pub = new JTextField();
		b_op = new JLabel("분류");
		bf_op = new Choice(); 
		sub_b = new JLabel("세부분류");
		sub_bf = new Choice();
		
		p_east = new JPanel();
		bt_add = new JButton("등록");
		bt_can = new JButton("취소");
		
		//스타일
		p_west.setLayout(new FlowLayout());
		p_west.setPreferredSize(new Dimension(390,500));
		
		p_center.setPreferredSize(new Dimension(480,600));
		Font f = new Font("돋움", Font.BOLD, 30);
		Dimension d = new Dimension(440, 30);
		b_name.setFont(f);
		bf_name.setPreferredSize(d);
		b_plot.setFont(f);
		bf_plot.setPreferredSize(d);
		scroll.setPreferredSize(new Dimension(450, 100));
		b_writer.setFont(f);
		bf_writer.setPreferredSize(d);
		b_pub.setFont(f);
		bf_pub.setPreferredSize(d);
		b_op.setFont(f);
		bf_op.setPreferredSize(new Dimension(470, 30));
		sub_bf.setPreferredSize(new Dimension(470, 30));
		can.setPreferredSize(new Dimension(370,440));
		p_east.setPreferredSize(new Dimension(300,300));
		bt_add.setPreferredSize(new Dimension(250,100));
		bt_add.setFont(new Font("돋움", Font.BOLD, 40));
		bt_can.setPreferredSize(new Dimension(250,100));
		bt_can.setFont(new Font("돋움", Font.BOLD, 40));
		sub_b.setFont(f);
		p_center.setBackground(new Color(0,0,0,10));
		p_east.setBackground(new Color(0,0,0,0));
		bf_plot.setLineWrap(true);
		
		//조립
		p_west.add(can);
		p_west.add(bt_file);
		add(p_west, BorderLayout.WEST);
		
		p_center.add(b_name);
		p_center.add(bf_name);
		p_center.add(b_plot);
		p_center.add(scroll);
		p_center.add(b_writer);
		p_center.add(bf_writer);
		p_center.add(b_pub);
		p_center.add(bf_pub);
		p_center.add(b_op);
		p_center.add(bf_op);
		p_center.add(sub_b);
		p_center.add(sub_bf);
		add(p_center, BorderLayout.CENTER);
		
		p_east.add(bt_add);
		p_east.add(bt_can);
		add(p_east, BorderLayout.EAST);
		
		//이벤트
		bt_file.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findLocal();
			}
		});
		bt_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thread = new Thread() {
					public void run() {
					regist();
					intoData();
					}
				};
				thread.start();
				addmain.getList();
			}
		});
		createTopcategory();
		bf_op.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				createSubcategory();
			}
		});
		sub_bf.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				getSub();
				
			}
		});
		bt_can.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancle();
				
			}
		});
		//조립
		
//		setVisible(true);
//		setSize(1200, 1200);
	}
	public void getSub() {
		String sql = "select * from subcategory where sub_name = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
		pstmt = this.getAppMain().getCon().prepareStatement(sql);
		pstmt.setString(1, sub_bf.getSelectedItem().toString());
		rs = pstmt.executeQuery();
		while(rs.next()) {
			sub_category = rs.getInt("subcategory_id");
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
	} finally {
		getAppMain().release(pstmt, rs);
	}
}
	public void findLocal() {
		if(chooser.showOpenDialog(this.getAppMain())==JFileChooser.APPROVE_OPTION) {
			File file=chooser.getSelectedFile();
			image = can.createImage(file.getAbsolutePath());
			can.setImage(image);
			ext = mng.getExtend(file.getAbsolutePath(), "/");
			can.repaint();
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		};
		
	}
	public void regist() {
		if(fis!=null) {
			
			int data = -1;
			try {
				Date date_now = new Date(System.currentTimeMillis());
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmsss");
				time = format.format(date_now);
				fos = new FileOutputStream("./res//"+time+"."+ext);
				while(true) {
					data = fis.read();
					if(data == -1)break;
					fos.write(data);
				}
			} catch (FileNotFoundException e) {
				System.out.println("파일선택 안해서");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(fis!=null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(fos!=null) {
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}				
			
		}else {
			//System.out.println();
		}
	}
	public void createTopcategory() {
//		ArrayList<String> topCategory = new ArrayList<String>();
//		ArrayList<String> subCategory = new ArrayList<String>();
		
		String sql = "select * from topcategory";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		bf_op.add("Choice Category");
		try {
			pstmt = this.getAppMain().getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				bf_op.add(rs.getString("top_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void createSubcategory() {
		sub_bf.removeAll();
		sub_bf.add("Choice Subcategory");
		String sql = "select * from subcategory where topcategory_id = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = this.getAppMain().getCon().prepareStatement(sql);
			pstmt.setInt(1, bf_op.getSelectedIndex());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				sub_bf.add(rs.getString("sub_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public void intoData() {
	PreparedStatement pstmt = null;
		ResultSet rs = null;


	
		StringBuilder sb= new StringBuilder();
		String sql = "insert into product(subcategory_id,product_name,author,brand,detail,filename)";
		sb.append(sql);
		sb.append(" values(?,?,?,?,?,?)");
		pstmt = null;
		rs = null;
		//System.out.println(sub_category);
		try {
			pstmt = this.getAppMain().getCon().prepareStatement(sb.toString());
			pstmt.setInt(1, sub_category);
			pstmt.setString(2, bf_name.getText());
			pstmt.setString(3, bf_writer.getText());
			pstmt.setString(4, bf_pub.getText());
			pstmt.setString(5, bf_plot.getText());
			pstmt.setString(6, time+"."+ext);
			int result = pstmt.executeUpdate();
			if(result == 1) {
				JOptionPane.showMessageDialog(this.getAppMain(), "도서 등록이 성공적으로 이루어졌습니다!");
				clearData();
			}else {
				JOptionPane.showMessageDialog(this.getAppMain(), "도서 등록 중 에러가 발생했습니다..");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
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
	}
	public void clearData() {
		bf_name.setText("");
		bf_writer.setText("");
		bf_pub.setText("");
		bf_plot.setText("");
		can.setImage(null);
		can.repaint();
		getAppMain().showHide(5);
	}
	public void cancle() {
		clearData();
	}


}
