package com.group.bookloan.main;

import java.awt.Canvas;
import java.awt.Dimension;

public class SubCanvas extends Canvas{
	Dimension d;
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SubCanvas(Dimension d) {
		this.d = d;
		
		this.setPreferredSize(d);
	}
	
}
