package com.group.bookloan.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.group.bookloan.admin.AddForm;
import com.group.bookloan.admin.AdminMain;
import com.group.bookloan.admin.RegistAdmin;
import com.group.bookloan.admin.SearchUser;
import com.group.bookloan.admin.UpdateForm;
import com.group.bookloan.user.LoginForm;
import com.group.bookloan.user.MainPage;
import com.group.bookloan.user.MyPage;
import com.group.bookloan.user.RegistForm;
import com.group.bookloan.user.SearchMain;

public class AppMain extends JFrame implements ActionListener{
	JPanel p_north;
	String[] menu_title = {"로그인","메인으로", "내 서재","로그아웃", "책 추가","도서관리","회원관리","로그아웃"};
	static Sessions sess = new Sessions();
	
	//
	CustomButton[] bt_menu = new CustomButton[menu_title.length];

	//CustomButton bt_mid = new CustomButton("전체목록보기");
	//
	//페이지 교체용 패널
	JPanel p_center;
	//페이지 선언
	static Page[] pages = new Page[11];
	//데이터베이스 관련 변수
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/bookloan?characterEncoding=UTF-8";
	String user = "root";
	String password = "1234";
	private Connection con;
	private boolean session = false;
	private boolean isAdmin = false;
	public AppMain() {
		
		connect();
		pack();
		//생성
		p_north = new JPanel();

		//비회원 버튼
		for(int i = 0; i <menu_title.length ; i ++) {
			bt_menu[i] = new CustomButton(menu_title[i]);
			bt_menu[i].setId(i);//버튼마다 식별용 ID를 부여함.
			p_north.add(bt_menu[i]);
		}
	
		p_center = new JPanel();
		pages[0] = new LoginForm(this);
		pages[1] = new MainPage(this);
		pages[2] = new MyPage(this);
		pages[3] = new SearchMain(this,(MainPage)pages[1]);
		pages[4] = new RegistForm(this);
		pages[5] = new AdminMain(this);
		pages[6] = new RegistForm(this);
		pages[7] = new RegistAdmin(this);
		pages[8] = new AddForm(this);
		pages[9] = new SearchUser(this);
		pages[10] = new UpdateForm(this, (AdminMain)pages[5]);
		
		
		//임시디자인
		p_north.setPreferredSize(new Dimension(1200,35));
		
		//붙이기
		add(p_north,BorderLayout.NORTH);
		add(p_center);
		for(int i = 0; i < pages.length ; i++) {
			p_center.add(pages[i]);			
		}
		
		
		//보이기
		setBounds(600,100,1200,700);
		setVisible(true);
		if(session == false) {
			showHide(1);
			returnmain();
		}
		//리스너

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			};
		});
		for(int i = 0 ; i < bt_menu.length; i++) {
			bt_menu[i].addActionListener(this);
		}
	}
	//어떤 버튼이 눌렸는지.
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		CustomButton cbt = (CustomButton)obj;
		
		if(cbt.getId()==3||cbt.getId()==7) {
			showHide(0);
			setSession(false);
			setAdmin(false);
			returnmain();
			sess.setAdmin_id(0);
			sess.setUser_id(0);
		}else if(cbt.getId()<=1) {
			showHide(cbt.getId());
			returnmain();
		}else if(session) {
			showHide(cbt.getId());
			//System.out.println(cbt.getId());
			returnmain();
		}else if(isAdmin) {
			if(cbt.getId()==5) {
				showHide(cbt.getId());
			}else if(cbt.getId()>5){
				showHide(cbt.getId()+3);
			}else {
				showHide(cbt.getId()+4);
			}
			returnmain();
		}
	}
	public void connect() {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,password);
			if(con != null) {
				this.setTitle("책 대여 프로그램 ver0.01 DB접속 성공");
			}else {
				this.setTitle("접속 에러??");				
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void disconnect() {
		if(con!=null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//쿼리닫기 모음
	//쿼리가 DML인 경우
	public void release(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	//쿼리가select문인 경우
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
	//버튼활성화
	public void returnmain() {
		if(session) {
			//System.out.println("호출 ㅎ");
			bt_menu[0].setVisible(false);
			for(int i = 0; i < bt_menu.length ; i ++) {
				if(i > 1 && i <4) {
					bt_menu[i].setVisible(true);					
				}
			}
		}else if(isAdmin) {
			bt_menu[0].setVisible(false);
			bt_menu[1].setVisible(false);
			for(int i = 4; i < bt_menu.length; i++) {
				bt_menu[i].setVisible(true);
			}
		}else if(session == false) {
			logOut();
		}else if(isAdmin == false) {
			logOut();
		}
		
	}
	//DB접속 게터/세터
	public Connection getCon() {
		return con;
	}
	public void setCon(Connection con) {
		this.con = con;
	}
	//페이지전환
	public void showHide(int n) {
		for(int i = 0; i < pages.length; i++) {
			if(i==n) {
				pages[i].setVisible(true);
			}else {
				pages[i].setVisible(false);
			}
		}
	};
	//로그인유무 체크
	//유저
	public boolean isSession() {
		return session;
	}
	public void setSession(boolean session) {
		this.session = session;
	}
	//관리자
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public void logOut() {
		for(int i = 0; i < bt_menu.length ; i++) {
			if(i > 1) {
				bt_menu[i].setVisible(false);					
			}else{
				bt_menu[i].setVisible(true);
			}
		}
	}
	public Sessions getSess() {
		return sess;
	}
	public void setSess(Sessions sess) {
		this.sess = sess;
	}
	public static void main(String[] args) {
		new AppMain();
	}
}
