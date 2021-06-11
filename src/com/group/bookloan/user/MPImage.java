package com.group.bookloan.user;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class MPImage extends JPanel{
	Toolkit kit;
	Image image;
	String dir = "C:\\Korea_Workspace\\korea202102_javaworkspace\\LibraryApp\\res\\";
	String[] pathArray= {"why.jpg", "xistory.jpg"};
	
	public MPImage() {
		kit = Toolkit.getDefaultToolkit();
		image = kit.getImage(dir+"why.jpg");
		image = image.getScaledInstance(200, 230, Image.SCALE_SMOOTH);
	}
	
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, this);
	}
}
