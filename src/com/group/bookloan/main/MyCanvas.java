package com.group.bookloan.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class MyCanvas extends Canvas{
	Image image;
	Toolkit kit = Toolkit.getDefaultToolkit();
	int width;
	int height;
	public MyCanvas(int width, int height) {
		this.width = width;
		this.height =height;
	}
	public Image createImage(String path) {
		image = kit.getImage(path);
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, width, height, this);
	}
	public String filename;
	public int id;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	private String Booktitle;
	
	private boolean canloan;
	
	public String getBooktitle() {
		return Booktitle;
	}
	public void setBooktitle(String booktitle) {
		Booktitle = booktitle;
	}
	public boolean isCanloan() {
		return canloan;
	}
	public void setCanloan(boolean canloan) {
		this.canloan = canloan;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
