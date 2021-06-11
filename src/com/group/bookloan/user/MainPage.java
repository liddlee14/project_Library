package com.group.bookloan.user;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.group.bookloan.detail.DetailMain;
import com.group.bookloan.main.AppMain;
import com.group.bookloan.main.CustomButton;
import com.group.bookloan.main.MyCanvas;
import com.group.bookloan.main.Page;

public class MainPage extends Page implements ActionListener, MouseListener{
	Image image;
	
	String[] menu_sub = {"신작","인기","추천"};
	JPanel p_north;
	JPanel p_center;
	JPanel p_south;
	MyCanvas[] mcans = new MyCanvas[8];
	Page[] pages = new Page[3];
	
	ArrayList<String>filename;
	ArrayList<String>booktitle;
	
	JButton bt;
	CustomButton[] bt_sub = new CustomButton[menu_sub.length];
	DetailMain detailMain;
	public MainPage(AppMain appMain) {
		super(appMain);
		createCanvas();
		pages[0]=new NewestPage(this);//신작페이지
		pages[1]=new PopularPage(this);//인기페이지
		pages[2]=new RecommendPage(this);
		p_north = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		createCanvases();
		for(int i = 0; i< menu_sub.length ; i++) {
			bt_sub[i] = new CustomButton(menu_sub[i]);
			bt_sub[i].setId(i);
			p_center.add(bt_sub[i]);
		}
		bt = new JButton("전체 리스트 보기");
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAppMain().showHide(3);
			}
		});
		//디자인
		p_north.setPreferredSize(new Dimension(1200,280));
		p_north.setBackground(new Color(255,0,0,0));
		p_center.setPreferredSize(new Dimension(1200,35));
		p_center.setBackground(new Color(255,0,0,0));
		p_south.setPreferredSize(new Dimension(1200,285));
		//p_south.setBackground(new Color(255,0,0,0));
		p_center.setLayout(new FlowLayout(FlowLayout.LEFT));
		//조립
		for(Canvas can : mcans) {
			p_north.add(can,BorderLayout.NORTH);
		}
//		for(JButton bt : bt_sub) {
//			p_center.add(bt);
//		}
		for(int i = 0; i < pages.length ; i++) {
			p_south.add(pages[i]);
		}

		
		add(p_north,BorderLayout.NORTH);
		p_north.add(bt,BorderLayout.SOUTH);
		add(p_center);
		add(p_south,BorderLayout.SOUTH);
		
		for(int i = 0; i < bt_sub.length ; i++) {
			bt_sub[i].addActionListener(this); 	
		}
		
		
		for(int i = 0 ; i<8 ; i ++) {
			mcans[i].addMouseListener(this);			
		}
					
	

		
		//보이기
		setVisible(true);
		
	}
	//버튼이벤트
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		CustomButton cbt = (CustomButton)obj;
		//System.out.println(cbt.getId());
		showHide(cbt.getId());

	}
	
	public void createCanvas() {
		filename = new ArrayList<String>();
		booktitle = new ArrayList<String>();
		String sql = "select filename,product_name from product order by product_id desc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = this.getAppMain().getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				filename.add(rs.getString("filename"));
				booktitle.add(rs.getString("product_name"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//캔버스이벤트
	public void mouseReleased(MouseEvent e) {
		Object obj = e.getSource();
		MyCanvas mc = (MyCanvas)obj;
		new DetailMain(mc.getBooktitle(),mc.getFilename(),this);
		//System.out.println(mc.getFilename());
	}
//		for(int i = 0; i<8;i++) {
//			System.out.println(filename.get(i));
//			mcans[i] = new MyCanvas(180,180); 
//			p_north.add(mcans[i],BorderLayout.NORTH);
//			mcans[i].setId(i);
//			
//			mcans[i].addMouseListener(this);			
//			mcans[i].setBackground(Color.cyan);
//			System.out.println(mcans[i].getId());
//			
//			
//			//mcans[i].repaint();
//		}
	public void showHide(int n) {
		for(int i = 0; i < pages.length; i++) {
			if(i==n) {
				pages[i].setVisible(true);
				pages[i].updateUI();
			}else {
				pages[i].setVisible(false);
			}
		}
	}
	public void createCanvases() {
		for(int i = 0; i<8;i++) {
			mcans[i] = new MyCanvas(140,180) ;
			image = mcans[i].createImage("./res//"+filename.get(i));
			
			//System.out.println(image);
			mcans[i].setImage(image);
			mcans[i].setId(i);
			mcans[i].setPreferredSize(new Dimension(140,180));
			//mcans[i].setBackground(Color.cyan);
			mcans[i].setFilename(filename.get(i));
			mcans[i].setBooktitle(booktitle.get(i));

		}
	}
	public void reload() {
		p_south.removeAll();
		for(int i = 0; i < pages.length ; i++) {
			p_south.add(pages[i]);
		}
	}
	public Page usePage(int p) {
		return pages[p];
	}
	public void mouseClicked(MouseEvent e) {	
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	};
	public MainPage getMainpage() {
		return this;
	}
}
