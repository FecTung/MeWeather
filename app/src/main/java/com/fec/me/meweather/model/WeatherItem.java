package com.fec.me.meweather.model;

/**
 * Created by kami on 03.27.2016.
 */
public class WeatherItem {

	private String date;
	private String weather;
	private String temp;
	private String wind;

	public WeatherItem(String date, String temp, String weather, String wind) {
		this.date = date;
		this.temp = temp;
		this.weather = weather;
		this.wind = wind;
	}

	public String getDate() {
		return date;
	}

	public String getTemp() {
		return temp;
	}

	public String getWeather() {
		return weather;
	}

	public String getWind() {
		return wind;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}
}
