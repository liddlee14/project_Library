package com.group.bookloan.detail;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.group.bookloan.main.CustomButton;

public class Header extends JPanel{
	String[] menu_title = {"로그인", "메인으로", "내 서재", "로그아웃", "책 추가", "수정&삭제", "회원관리", "로그아웃"};
	CustomButton[] bt_menu = new CustomButton[menu_title.length];
	
	public Header() {
		/****** 생성 ******/
		for(int i = 0; i <menu_title.length ; i ++) {
			bt_menu[i] = new CustomButton(menu_title[i]);
			bt_menu[i].setId(i);//버튼마다 식별용 ID를 부여함.
		}		
		
		
		
		
		setPreferredSize(new Dimension(1150, 50));
		
		for(int i = 0; i <menu_title.length ; i ++) {
			add(bt_menu[i]);
		}
		
	}
}
 