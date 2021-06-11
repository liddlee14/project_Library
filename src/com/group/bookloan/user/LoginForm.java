package com.group.bookloan.user;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.group.bookloan.main.AppMain;
import com.group.bookloan.main.Page;
import com.group.bookloan.main.Sessions;



public class LoginForm extends Page{

	JPanel p_container;
	JPanel p_center;
	JPanel p_south;
	JLabel la_id, la_pass;
	JTextField t_id;
	JPasswordField t_pass;
	JButton bt_login, bt_join;
	Choice ch_login;
	AppMain appMain;
	Sessions sess;
	
	public LoginForm(AppMain appMain) {
		super(appMain);
		this.appMain = appMain;
		p_container = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		la_id = new JLabel("ID");
		la_pass = new JLabel("Password");
		t_id = new JTextField();
		t_pass = new JPasswordField();
		bt_login = new JButton("로그인");
		bt_join = new JButton("회원가입");
		ch_login = new Choice();
		
		ch_login.add("User");
		ch_login.add("Admin");
		
		//스타일 레이아웃
		p_container.setPreferredSize(new Dimension(325, 125));
		p_container.setLayout(new BorderLayout());
		p_center.setLayout(new GridLayout(2,2));
		p_center.setBackground(new Color(0, 0, 0, 0)); 
		p_south.setBackground(new Color(0, 0, 0, 0)); 
		p_container.setBackground(new Color(0, 0, 0, 0)); 
		
		p_center.add(la_id);
		p_center.add(t_id);
		p_center.add(la_pass);
		p_center.add(t_pass);
		
		p_south.add(ch_login);
		p_south.add(bt_login);
		p_south.add(bt_join);
		
		p_container.add(p_center);
		p_container.add(p_south, BorderLayout.SOUTH);
		
		
		add(p_container);
		
		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAppMain().showHide(4);
				
			}
		});
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginCheck();
				
			}
		});
		
		setBounds(600,100,1200,700);
		setVisible(true);
	}
	public void loginCheck() {
		String sql = null;
		if(ch_login.getSelectedItem()=="User") {
			sql = "select * from userdata where u_id = ? and u_pass=?";			
		}else if(ch_login.getSelectedItem()=="Admin") {
			sql = "select * from admindata where ad_id = ? and ad_pass=?";						
		}
		//System.out.println(sql);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String pass = RegistForm.p_encrypt(new String(t_pass.getPassword()));
			pstmt = this.getAppMain().getCon().prepareStatement(sql);
			pstmt.setString(1, t_id.getText());
			pstmt.setString(2, pass);
			rs = pstmt.executeQuery();
			//로그인확인
			if(rs.next()) {
				sess = new Sessions();
				if(ch_login.getSelectedItem()=="User") {
					JOptionPane.showMessageDialog(this.getAppMain(), "환영합니다!");
					getAppMain().setSession(true);
					getAppMain().returnmain();
					getAppMain().showHide(1);	
					sess.setUser_id(rs.getInt("user_id"));
					getAppMain().setSess(sess);
				}else if(ch_login.getSelectedItem()=="Admin") {
					JOptionPane.showMessageDialog(this.getAppMain(), "관리자 로그인이 성공하였습니다.");
					getAppMain().setAdmin(true);
					getAppMain().returnmain();
					getAppMain().showHide(5);
					sess.setAdmin_id(rs.getInt("admin_id"));
					getAppMain().setSess(sess);
				}
			}else {
				JOptionPane.showMessageDialog(this.getAppMain(), "잘못된 아이디 또는 패스워드입니다.");
				//System.out.println(ch_login.getSelectedItem());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			this.getAppMain().release(pstmt, rs);
		}
	}

	
}
