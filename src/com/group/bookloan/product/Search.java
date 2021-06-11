package com.group.bookloan.product;

public class Search {
	private String product_name;
	private String day_end;
	private Boolean canloan;
	private Boolean canextend;
	private int product_id;
	private int subcategory_id;
	public int getSubcategory_id() {
		return subcategory_id;
	}
	public void setSubcategory_id(int subcategory_id) {
		this.subcategory_id = subcategory_id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	private String author;
	private String detail;
	private String brand;
	
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getDay_end() {
		return day_end;
	}
	public void setDay_end(String day_end) {
		this.day_end = day_end;
	}
	public Boolean getCanloan() {
		return canloan;
	}
	public void setCanloan(Boolean canloan) {
		this.canloan = canloan;
	}
	public Boolean getCanextend() {
		return canextend;
	}
	public void setCanextend(Boolean canextend) {
		this.canextend = canextend;
	}
	
}
