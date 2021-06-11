package com.group.bookloan.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import com.group.bookloan.main.AppMain;
import com.group.bookloan.main.MyCanvas;
import com.group.bookloan.main.Page;

public class MPMain extends JPanel{
	Page mp;
	JPanel mp_p;
	
	//이미지
	JPanel mp_p2;
	Image mp_img;
	MyCanvas mc;
	//제목 + 테이블 판넬
	JPanel[] p;
	JPanel mp_p3;
	JPanel mp_p3n;
	JPanel mp_p3s;

	//제목
	JLabel mp_title;
	
	//정보 테이블
	String[] columns= {"대여일", "반납예정일", "연기", "연체일"};
	JTable mp_table;
	JScrollPane scroll;
	
	//버튼
	JButton mp_bt;
	JButton mp_bt2;
	boolean extend;
	String end;
	String ext;
	int id;
	int p_id;
	String newdate;
	AppMain appMain;
	public MPMain(int id, int p_id, String name, String start,String end,String filename, boolean extend, AppMain appmain,String ext,long caldate, MyPage mp) {
		this.p_id = p_id;
		this.appMain = appmain;
		this.extend = extend;
		this.id = id;
		this.end = end;
		this.ext = ext;
		String[][] rows= {
				{start, end, ext, Long.toString(caldate)}
		};
		if(caldate >0) {
			mp_table.setForeground(Color.red);
		}
		//생성
		mc = new MyCanvas(200,230);
		mc.setPreferredSize(new Dimension(200,230));
		mp_p = new JPanel();
		mp_p2 = new JPanel();
		mp_img = mc.createImage(".//res//"+filename);
		mc.setImage(mp_img);
		Image image = mc.createImage(".//res//"+filename);
		mc.setImage(image);
		
		
		mp_p3 = new JPanel();
		mp_p3n = new JPanel();
		mp_p3s = new JPanel();
		mp_title = new JLabel(name);
		mp_table = new JTable(rows, columns) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		scroll = new JScrollPane(mp_table);
		
		mp_bt = new JButton("연장");
		mp_bt2 = new JButton("반납");
		
		//스타일
		//mp_p1
		mp_p.setBackground(new Color(0,0,0,0));
		mp_p2.setPreferredSize(new Dimension(200, 230));
		mp_title.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		
		//mp_p3
		mp_p3.setPreferredSize(new Dimension(530, 230));
		mp_p3.setBackground(new Color(0,0,0,0));
		
		//mp_p4&p5
		mp_p3n.setPreferredSize(new Dimension(530, 110));
		mp_p3s.setPreferredSize(new Dimension(530, 110));
		mp_p3n.setBackground(new Color(0,0,0,0));
		mp_p3s.setBackground(new Color(0,0,0,0));
		
		//table

		JTableHeader mp_tableheader = mp_table.getTableHeader();
		mp_tableheader.setPreferredSize(new Dimension(100, 50));
		mp_tableheader.setFont(new Font("굴림", Font.BOLD, 20));
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalTextPosition(DefaultTableCellRenderer.CENTER);
		mp_table.setRowHeight(50);
		mp_table.setFont(new Font("굴림", Font.BOLD, 20));
		
		mp_table.getColumnModel().getColumn(0).setPreferredWidth(120);
		mp_table.getColumnModel().getColumn(1).setPreferredWidth(120);
		mp_table.getColumnModel().getColumn(2).setPreferredWidth(25);
		mp_table.getColumnModel().getColumn(3).setPreferredWidth(30);
		 
		mp_table.getTableHeader().setReorderingAllowed(false);
		mp_table.getTableHeader().setResizingAllowed(false);
		
		//이벤트
		mp_bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(extend) {
					extend();	
					updateextend();
					JOptionPane.showMessageDialog(appMain, "연장처리가 완료되었습니다.");
					mp.reLoad();
				}else {
					JOptionPane.showMessageDialog(appMain, "더 이상 연장할 수 없습니다.");
				}
				
			}
		});
		mp_bt2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnbooks();
				updateextend2();
				//System.out.println(p_id);
				mp.reLoad();
				
			}
		});
		//조립
		mp_p2.add(mc);
		
		mp_p.add(mp_p2);
		mp_p3.add(mp_p3n, BorderLayout.NORTH);
		mp_p3.add(mp_p3s, BorderLayout.SOUTH);
		mp_p.add(mp_p3);
		mp_p3n.add(mp_title);
		mp_p3s.add(scroll);
		mp_p.add(mp_bt);
		mp_p.add(mp_bt2);
		add(mp_p);
		
		//보여주기
		setSize(600,400);
		setVisible(true);
		
		
	}
	public void extend() {
		String from = end;
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		try {
			Date to = fm.parse(end);
			cal.setTime(to);
			cal.add(Calendar.DATE, 6);
			newdate = fm.format(cal.getTime());
			
			PreparedStatement pstmt = null;
			String sql = "update loanedbook set day_end = ? where product_id = ?";
			//System.out.println(newdate);
			try {
				pstmt = appMain.getCon().prepareStatement(sql);
				pstmt.setString(1, newdate);
				pstmt.setInt(2, p_id);
				int result = pstmt.executeUpdate();
				if(result ==1) {
					
				}else {
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				appMain.release(pstmt);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}finally {
			
		}
	}
	public void updateextend() {
		String sql = "update product set canextend = false where product_id = "+p_id;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = appMain.getCon().prepareStatement(sql);
			int result = pstmt.executeUpdate();
			if(result == 1) {
				extend = false;
			}else {
				JOptionPane.showMessageDialog(appMain,"에러 발생!!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			appMain.release(pstmt);
		}
	}
	public void updateextend2() {
		String sql = "update product set canextend = true, canloan = true where product_id = "+p_id;
		PreparedStatement pstmt = null;
		
		try {
			pstmt = appMain.getCon().prepareStatement(sql);
			int result = pstmt.executeUpdate();
			if(result == 1) {
				extend = false;
			}else {
				JOptionPane.showMessageDialog(appMain,"에러 발생!!!!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			appMain.release(pstmt);
		}

	}
	public void returnbooks() {
		PreparedStatement pstmt = null;
		String sql = "delete from u, l using userbooks as u left join loanedbook as l on l.product_id = u.product_id where l.product_id = "+p_id;
		try {
			pstmt = appMain.getCon().prepareStatement(sql);
			int result = pstmt.executeUpdate();
			if(result > 0) {
				JOptionPane.showMessageDialog(appMain, "반납요청이 완료되었습니다.");
				
			}else {
				JOptionPane.showMessageDialog(appMain, "반납요청중 에러가 발생했습니다, 이미 반납이 완료되었을 수 있습니다.");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			appMain.release(pstmt);
		}
		
	}
}
