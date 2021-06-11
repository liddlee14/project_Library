package com.group.bookloan.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.group.bookloan.main.AppMain;
import com.group.bookloan.main.Page;

public class RegistForm extends Page{
	JPanel p_container;
	JPanel p_center;
	JPanel p_south;
	JLabel la_id,la_pass,la_name,la_phone,la_address;
	JTextField t_id, t_name, t_phone,t_address;
	JPasswordField t_pass;
	JButton bt_login, bt_join, bt_admin;
	
	public RegistForm(AppMain appMain) {
		super(appMain);
		
		//생성
		p_container = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		la_id = new JLabel("ID");
		la_pass = new JLabel("Password");
		la_name = new JLabel("Name");
		la_phone = new JLabel("PhoneNo.");
		la_address = new JLabel("e-Mail");
		t_id = new JTextField();
		t_name = new JTextField();
		t_phone = new JTextField();
		t_address = new JTextField();
		t_pass = new JPasswordField();
		bt_login = new JButton("Login");
		bt_join = new JButton("회원가입");
		bt_admin = new JButton("관리자 등록");
		//디자인
		p_container.setPreferredSize(new Dimension(325,155));
		p_container.setLayout(new BorderLayout());
		p_center.setLayout(new GridLayout(5,2));
		p_center.setBackground(new Color(0, 0, 0, 0)); 
		p_container.setBackground(new Color(0, 0, 0, 0)); 
		p_south.setBackground(new Color(0, 0, 0, 0));
		
		//조립
		p_center.add(la_id);
		p_center.add(t_id);
		p_center.add(la_pass);
		p_center.add(t_pass);
		p_center.add(la_name);
		p_center.add(t_name);
		p_center.add(la_phone);
		p_center.add(t_phone);
		p_center.add(la_address);
		p_center.add(t_address);
		
		p_south.add(bt_login);
		p_south.add(bt_join);
		p_south.add(bt_admin);
		
		p_container.add(p_center);
		p_container.add(p_south, BorderLayout.SOUTH);
		
		add(p_container);
		
		//리스너
		bt_join.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(t_id.getText().length()==0) {
					JOptionPane.showMessageDialog(RegistForm.this.getAppMain(), "아이디를 입력하세요");
					return;
				}
				if(new String (t_pass.getPassword()).length()==0) {
					JOptionPane.showMessageDialog(RegistForm.this.getAppMain(), "비밀번호를 입력하세요");
					return;
				}
				if(t_name.getText().length()==0) {
					JOptionPane.showMessageDialog(RegistForm.this.getAppMain(), "이름을 입력하세요");
					return;
				}
				if(t_phone.getText().length()!=13) {
					JOptionPane.showMessageDialog(RegistForm.this.getAppMain(), "'-'을 포함한 13자리 숫자여야 합니다");
					return;
					
				}
				if(t_address.getText().length()==0) {
					JOptionPane.showMessageDialog(RegistForm.this.getAppMain(), "이름을 입력하세요");
					return;
				}
				regist();
			}
		});
		bt_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistForm.this.getAppMain().showHide(0);
				
			}
		});
		bt_admin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistForm.this.getAppMain().showHide(7);
				
			}
		});
		//
		
	}
	public static String p_encrypt(String password) {
		String pass = password;
		MessageDigest md;
		String cryptpass = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte[]hash = md.digest(pass.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < hash.length ; i++) {
				String hex = Integer.toHexString(0xff &hash[i]);
				if(hex.length() == 1) sb.append("0");
				sb.append(hex);
			}
			cryptpass = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return cryptpass;
	}
	public void regist() {
		String sql = "insert into userdata(u_id, u_pass, u_name, u_phone,u_address) values(?,?,?,?,?)";
		PreparedStatement pstmt = null;
		String pass = p_encrypt(new String(t_pass.getPassword()));
		try {
			pstmt = this.getAppMain().getCon().prepareStatement(sql);
			pstmt.setString(1, t_id.getText());
			pstmt.setString(2, pass);
			pstmt.setString(3, t_name.getText());
			pstmt.setString(4, t_phone.getText());
			pstmt.setString(5, t_address.getText());
			
			int result = pstmt.executeUpdate();
			if(result == 1) {
				JOptionPane.showMessageDialog(this.getAppMain(), "환영합니다! "+t_name.getText()+" 님");
				this.getAppMain().showHide(0);
			}else {
				JOptionPane.showMessageDialog(this.getAppMain(), "회원가입 중 문제가 발생했습니다. 다시 시도해주세요.");
			}
		} catch (SQLException e) {
	
			e.printStackTrace();
		}finally {
			this.getAppMain().release(pstmt);
		}
	}
}
