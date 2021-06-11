package com.group.bookloan.main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.ScrollPane;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.group.bookloan.admin.AdminMain;
import com.group.bookloan.user.MainPage;

public class Page extends JPanel{
	ImageIcon i = new ImageIcon("./res//background2.png");
	Image im = i.getImage();
	private AppMain appMain;
	private MainPage mainPage;
	private AdminMain adminMain;
	ScrollPane scroll;
	public MainPage getMainPage() {
		return mainPage;
		
	}

	public AdminMain getAdminMain() {
		return adminMain;
	}

	public void setAdminMain(AdminMain adminMain) {
		this.adminMain = adminMain;
	}

	public void setMainPage(MainPage mainPage) {
		this.mainPage = mainPage;
	}

	public AppMain getAppMain() {
		return appMain;
	}

	public void setAppMain(AppMain appMain) {
		this.appMain = appMain;
	}
	//setPreferredSize(new Dimension(1200,700));
	public Page() {
		
	};
	public Page(AppMain appMain) {
		this.appMain = appMain;
		setPreferredSize(new Dimension(1200,700));
		setVisible(false);
		
	}
	public Page(String title, String filename, MainPage mainPage) {
		this.mainPage = mainPage;
		
		
	}
	public Page(AppMain appMain, AdminMain adMain) {
		this.appMain = appMain;
		this.adminMain = adMain;
		setPreferredSize(new Dimension(1200,700));
		setVisible(false);
	}
	public Page(AppMain appMain, MainPage mainPage) {
		this.appMain = appMain;
		this.mainPage = mainPage;
		setPreferredSize(new Dimension(1200,700));
		setVisible(false);
	}
	
	public Page(MainPage mainPage) {
		this.mainPage = mainPage;
		setPreferredSize(new Dimension(1200,700));
		setVisible(false);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(im, 0, 0, 1200, 700, this);
	};
}
