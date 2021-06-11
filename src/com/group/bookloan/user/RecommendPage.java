package com.group.bookloan.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.group.bookloan.detail.DetailMain;
import com.group.bookloan.main.MyCanvas;
import com.group.bookloan.main.Page;

public class RecommendPage extends Page implements MouseListener{
	JPanel p1;
	JPanel p2;
	JPanel[] panels = new JPanel[6];
	Image image;
	MyCanvas[] s_cans = new MyCanvas[6];
	ArrayList<String> filename;
	ArrayList<String> booktitle;
	ArrayList<Boolean> canloan;
	
	JLabel[] labs = new JLabel[6];
	
	Dimension d1;
	Dimension d2;
	public RecommendPage(MainPage mainPage) {
		super(mainPage);
		createCanvas();
		//생성
		d1 = new Dimension(120,120);
		d2 = new Dimension(390,130);
		p1 = new JPanel();
		p2 = new JPanel();
		
		createPanels();
		
		repaint();

		//디자인
//		p1.setBackground(Color.red);
//		p2.setBackground(Color.orange);
//		panels[0].setBackground(Color.yellow);
//		panels[1].setBackground(Color.green);
//		panels[2].setBackground(Color.blue);
//		panels[3].setBackground(Color.black);
//		panels[4].setBackground(Color.cyan);
//		panels[5].setBackground(Color.gray);
		p1.setPreferredSize(new Dimension(1200,140));
		p2.setPreferredSize(new Dimension(1200,140));
		//배치
		add(p1,BorderLayout.NORTH);
		add(p2,BorderLayout.SOUTH);
		
		//panels[0].add(s_cans[0]);
		//panels[0].add(labs[0]);
		//add(p3);
		//setLayout(new BorderLayout());
		setBackground(Color.GRAY);
		setVisible(false);
		setPreferredSize(new Dimension(1200,800));
	}
	
	public void removePanel() {
		p1.removeAll();
		p2.removeAll();
		createPanels();
		setVisible(true);
	}
	
	public void createPanels() {
		createCanvas();
		for(int i = 0; i < 6; i++) {
			s_cans[i] = new MyCanvas(120,120);
			s_cans[i].setBackground(Color.red);
			s_cans[i].setId(i);
			s_cans[i].setFilename(filename.get(i));
			image = s_cans[i].createImage("./res//"+filename.get(i));
			s_cans[i].setImage(image);
			s_cans[i].setBooktitle(booktitle.get(i));
			if(canloan.get(i)) {
				labs[i] = new JLabel("대여 가능"); // 일단 붙여두고, 데이터 연동할떄 수정할수도?				
				labs[i].setForeground(new Color(0,183,0));
			}else {
				labs[i] = new JLabel("대여 불가"); // 일단 붙여두고, 데이터 연동할떄 수정할수도?								
				labs[i].setForeground(Color.red);
			}
			labs[i].setFont(new Font("굴림",Font.BOLD,20));
			panels[i] = new JPanel();
			panels[i].setPreferredSize(d2);
			panels[i].add(s_cans[i]);
			panels[i].add(labs[i]);
			s_cans[i].setPreferredSize(d1);
			s_cans[i].addMouseListener(this);
		}
		p1.add(panels[0],BorderLayout.WEST);
		p1.add(panels[1]);
		p1.add(panels[2],BorderLayout.EAST);
		p2.add(panels[3],BorderLayout.WEST);
		p2.add(panels[4]);
		p2.add(panels[5],BorderLayout.EAST);
		p1.setBackground(Color.DARK_GRAY);
		p2.setBackground(Color.DARK_GRAY);
		repaint();
	}
	
	public void createCanvas() {
		filename = new ArrayList<String>();
		booktitle = new ArrayList<String>();
		canloan = new ArrayList<Boolean>();
		String sql = "select filename,product_name,canloan from product order by brand asc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = this.getMainPage().getAppMain().getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				filename.add(rs.getString("filename"));
				booktitle.add(rs.getString("product_name"));
				//System.out.println(rs.getString("product_name"));
				//System.out.println(rs.getString("filename"));
				
				canloan.add(rs.getBoolean("canloan"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt !=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public void mouseReleased(MouseEvent e) {
		Object obj = e.getSource();
		MyCanvas mc = (MyCanvas)obj;
		new DetailMain(mc.getBooktitle(), mc.getFilename(),this.getMainPage());
		
	}

	
	
	public void mousePressed(MouseEvent e) {
		
	}
	public void mouseEntered(MouseEvent e) {	
	}
	public void mouseExited(MouseEvent e) {	
	}
	public void mouseClicked(MouseEvent e) {
	}
}
