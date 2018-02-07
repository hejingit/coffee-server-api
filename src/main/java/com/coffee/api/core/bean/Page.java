package com.coffee.api.core.bean;

public class Page {
	private int page = 1;
	private int rows = 10;
	public int getPage() {
		return page > 0 ? page : 1;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows > 0 ? rows : 10;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
}
