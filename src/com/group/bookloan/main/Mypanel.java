package com.group.bookloan.main;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Mypanel extends JPanel{
	ImageIcon i = new ImageIcon("./res//background2.png");
	Image im = i.getImage();
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		g.drawImage(im, 0, 0, 1200, 700, this);
	}
}
