package com.group.bookloan.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.group.bookloan.main.AppMain;
import com.group.bookloan.main.Page;
import com.group.bookloan.main.Sessions;

public class MyPage extends Page{
	JPanel p;
	JScrollPane scroll;
	JButton bt;
	MPMain mpm, mpm2, mpm3;
	Sessions sess;
	Scrollbar bar;
	AppMain appMain;
	String ext;
	int x;
	public MyPage(AppMain appMain) {
		super(appMain);
		this.appMain = appMain;
		
//		mpm = new MPMain();
//		mpm2 = new MPMain();
//		mpm3 = new MPMain();
		setLayout(new BorderLayout());
		p = new Page();
		scroll = new JScrollPane(p);
		scroll.setPreferredSize(new Dimension(1200,700));
		add(scroll);
		bt = new JButton("조회");
		bt.setPreferredSize(new Dimension(25,25));
		scroll.setPreferredSize(new Dimension(500,620));
		scroll.setBounds(4,4,300,300);
		p.setPreferredSize(new Dimension(500,200));
		p.setLayout(new FlowLayout());
		add(bt,BorderLayout.NORTH);
		
		setBackground((new Color(0,0,0,30)));
		setPreferredSize(new Dimension(1200,620));
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reLoad();
			}
		});
	}
	public void getList() {
		Vector<MPMain> mpmArray = new Vector<MPMain>();
		
		String sql = "select u.user_id, p.product_id ,product_name, day_start, day_end, filename, canextend from userbooks as b left join userdata as u on b.user_id = u.user_id left join product p on b.product_id = p.product_id left join loanedbook l on l.product_id = p.product_id where u.user_id ="+sess.getUser_id()+" group by p.product_id order by day_start asc";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData meta = null;
		//System.out.println(sess.getUser_id());
		try {
			pstmt = this.getAppMain().getCon().prepareStatement(sql);
			rs = pstmt.executeQuery();
			meta = rs.getMetaData();
			//pstmt.setInt(1, 1);
			int col_count = meta.getColumnCount();
			
			while(rs.next()) {
				x += 260;
				//System.out.println(rs.next());
				int user_id = rs.getInt("user_id");
				int product_id =rs.getInt("product_id");
				String product_name = rs.getString("product_name");
				String day_start = rs.getString("day_start");
				String day_end = rs.getString("day_end");
				String fn=rs.getString("filename");
				Boolean canextend = rs.getBoolean("canextend");
				//System.out.println(product_name);
				long caldate=0;
				if(canextend) {
					ext = "6일";
				}else {
					ext = "-";
				}
				if(timecheck(day_end) < 0) {
					caldate = 0;
				}else {
					overdue(user_id);
					caldate = timecheck(day_end);
				}
				createTable(new MPMain(user_id, product_id,product_name,day_start,day_end,fn,canextend,appMain,ext,caldate,this));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.getAppMain().release(pstmt);
		}
		
	}
	public void createTable(MPMain mpmain) {
		//
		p.add(mpmain);
		updateUI();
	}
	public long timecheck(String day_end) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String day = dateFormat.format(cal.getTime());
		long calDays = 0;
		try {
			Date today = dateFormat.parse(day);
			Date endday = dateFormat.parse(day_end);
			long calDate = today.getTime() - endday.getTime();
			calDays = calDate / (24*60*60*1000);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calDays;
		
		
	}
	public void overdue(int user_id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "update userdata set overdue = true where user_id = ? ";
		pstmt = null;
		rs = null;
		try {
			pstmt = getAppMain().getCon().prepareStatement(sql);
			pstmt.setInt(1, user_id);
			int result = pstmt.executeUpdate();
			if(result ==1 ) {
				JOptionPane.showMessageDialog(getAppMain(), "연체 도서가 있습니다!!");
			}else {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			getAppMain().release(pstmt, rs);
		}
	}
	public void reLoad() {
		sess = getAppMain().getSess();
		//System.out.println(sess.getUser_id());
		p.removeAll();
		getList();
		p.repaint();
		p.setPreferredSize(new Dimension(500,x));
	}
	public MyPage getMyPage() {
		return this;
	}
}