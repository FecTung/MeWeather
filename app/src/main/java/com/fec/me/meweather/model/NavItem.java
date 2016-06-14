package com.fec.me.meweather.model;

/**
 * Created by kami on 03.27.2016.
 */
public class NavItem {

	private String cityCode;
	private String cityName;

	public NavItem(String cityCode, String cityName) {
		this.cityCode = cityCode;
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getCityName() {
		return cityName;
	}
}
