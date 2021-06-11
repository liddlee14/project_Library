package com.group.bookloan.detail;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class RecommendBox extends JPanel{
	int width;
	int height;
	String title;
	String rentable;
	Color color;		//배경색
	Image img;
	Toolkit kit = Toolkit.getDefaultToolkit();
	public RecommendBox(int width, int height, String title, String rentable, Color color) {
		this.width=width;
		this.height=height;
		this.title=title;
		this.rentable=rentable;
		this.color = color;
		setPreferredSize(new Dimension(width, height));
	}
	public Image createImage(String path) {
		img = kit.getImage(path);
		return img;
	}
	public void setImage(Image img) {
		this.img = img;
	}
	public void paint(Graphics g) {
		g.setColor(color);//그래픽 객체의 물감색을 결정한다
		g.fillRect(0, 0, width, height);//채워진 사각형
		g.setColor(Color.black);//폰트 색깔 회색으로 설정
		g.drawString(title, 40, 160);

		if(rentable=="대여 가능") {
			//g.setFont(new Font("고딕", Font.BOLD, 14));
			g.setColor(new Color(0,183,0));//폰트 색깔 회색으로 설정
		}else {
			g.setColor(color.red);	}
		g.drawString(rentable, 40, 180);
		
		//조건에 따라 아이콘을 그리고 or 안 그리고를 결정 짓게 하자
		if(img!=null) {		//img가 null이 아닌 경우면 그리자! 평상시엔 안 그리자!!
			g.drawImage(img, 15, 12, 120, 135, this);
		}
	}
	


}
