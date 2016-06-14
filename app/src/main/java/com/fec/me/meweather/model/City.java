package com.fec.me.meweather.model;

public class City {
	private int id;
	private String cityCode;
	private String cityNameCN;
	private String cityNameEN;
	private String cityParent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityNameCN() {
		return cityNameCN;
	}

	public void setCityNameCN(String cityNameCN) {
		this.cityNameCN = cityNameCN;
	}

	public String getCityNameEN() {
		return cityNameEN;
	}

	public void setCityNameEN(String cityNameEN) {
		this.cityNameEN = cityNameEN;
	}

	public String getCityParent() {
		return cityParent;
	}

	public void setCityParent(String cityParent) {
		this.cityParent = cityParent;
	}
}
