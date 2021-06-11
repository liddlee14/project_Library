package com.group.bookloan.admin;

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

public class RegistAdmin extends Page{
	JPanel p_container;
	JPanel p_center;
	JPanel p_south;
	JLabel la_id,la_pass,la_name,la_code;
	JTextField t_id, t_name, t_code;
	JPasswordField t_pass;
	JButton bt_admin;
	
	public RegistAdmin(AppMain appMain) {
		super(appMain);
		
		//생성
		p_container = new JPanel();
		p_center = new JPanel();
		p_south = new JPanel();
		la_id = new JLabel("ID");
		la_pass = new JLabel("Password");
		la_name = new JLabel("Name");
		la_code = new JLabel("가입코드");
		t_id = new JTextField();
		t_name = new JTextField();
		t_code = new JTextField("1a2b");
		t_pass = new JPasswordField();

		bt_admin = new JButton("등록");
		//디자인
		p_container.setPreferredSize(new Dimension(250,125));
		p_container.setLayout(new BorderLayout());
		p_center.setLayout(new GridLayout(4,2));
		p_center.setBackground(new Color(0, 0, 0, 0));
		p_south.setBackground(new Color(0, 0, 0, 0));
		p_container.setBackground(new Color(0, 0, 0, 0));
		
		//조립
		p_center.add(la_id);
		p_center.add(t_id);
		p_center.add(la_pass);
		p_center.add(t_pass);
		p_center.add(la_name);
		p_center.add(t_name);
		p_center.add(la_code);
		p_center.add(t_code);
		
		p_south.add(bt_admin);

		
		p_container.add(p_center);
		p_container.add(p_south, BorderLayout.SOUTH);
		
		add(p_container);
		
		//리스너
		bt_admin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(t_id.getText().length()==0) {
					JOptionPane.showMessageDialog(RegistAdmin.this.getAppMain(), "아이디를 입력하세요");
					return;
				}
				if(new String (t_pass.getPassword()).length()==0) {
					JOptionPane.showMessageDialog(RegistAdmin.this.getAppMain(), "비밀번호를 입력하세요");
					return;
				}
				if(t_name.getText().length()==0) {
					JOptionPane.showMessageDialog(RegistAdmin.this.getAppMain(), "이름을 입력하세요");
					return;
				}
//				if(t_code.getText() != "1a2b") {
//					JOptionPane.showMessageDialog(RegistAdmin.this.getAppMain(), "잘못된 관리자 생성코드입니다.");
//					return;
//					
//				}
				regist();
			}
		});
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
		String sql = "insert into admindata(ad_id, ad_pass, ad_name) values(?,?,?)";
		PreparedStatement pstmt = null;
		String pass = p_encrypt(new String(t_pass.getPassword()));
		try {
			pstmt = this.getAppMain().getCon().prepareStatement(sql);
			pstmt.setString(1, t_id.getText());
			pstmt.setString(2, pass);
			pstmt.setString(3, t_name.getText());
			
			int result = pstmt.executeUpdate();
			if(result == 1) {
				JOptionPane.showMessageDialog(this.getAppMain(), "관리자 등록이 성공적으로 완료되었습니다.");
				this.getAppMain().showHide(1);
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
