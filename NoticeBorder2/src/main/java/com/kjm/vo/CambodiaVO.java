package com.kjm.vo;


public class CambodiaVO {
	private String year;
	private String id;
	private String io;
	private String product;

	
	public CambodiaVO() {
		super();
	}
	
	public CambodiaVO(String year, String id, String io,String product) {
		super();
		this.year = year;
		this.id = id;
		this.io = io;
		this.product = product;

	}
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIo() {
		return io;
	}
	public void setIo(String io) {
		this.io = io;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}

	
	@Override
	public String toString() {
		return "CambodiaVO [year=" + year + ", id=" + id + ", io=" + io + ", product=" + product + "]";
	}
}