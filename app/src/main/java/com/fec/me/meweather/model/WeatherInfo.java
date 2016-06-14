package com.fec.me.meweather.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WeatherInfo {

	public WeatherInfo(AqiEntity aqi, ForecastEntity forecast, RealtimeEntity realtime) {
		this.aqi = aqi;
		this.forecast = forecast;
		this.realtime = realtime;
	}

	/**
	 * city : 北京
	 * city_en : beijing
	 * cityid : 101010100
	 * date_y : 2016年03月12日
	 * temp1 : 9℃~1℃
	 * temp2 : 12℃~-1℃
	 * temp3 : 15℃~4℃
	 * temp4 : 15℃~4℃
	 * temp5 : 17℃~6℃
	 * temp6 : 0℃~0℃
	 * weather1 : 晴
	 * weather2 : 晴转多云
	 * weather3 : 多云
	 * weather4 : 多云
	 * weather5 : 多云
	 * weather6 : 晴
	 * wind1 : 微风
	 * wind2 : 微风
	 * wind3 : 微风
	 * wind4 : 微风
	 * wind5 : 微风
	 * wind6 : 微风
	 */
	private ForecastEntity forecast;

	/**
	 * SD : 52%
	 * WD : 东北风
	 * WS : 1级
	 * temp : 3
	 * time : 20:55
	 * weather : 晴
	 */
	private RealtimeEntity realtime;

	/**
	 * pub_time : 2016-03-12 20:00
	 * aqi : 184
	 * pm25 : 139
	 * src : 中国环境监测总站
	 */
	private AqiEntity aqi;

	public static WeatherInfo objectFromData(String str) {

		return new Gson().fromJson(str, WeatherInfo.class);
	}

	public static List<WeatherInfo> arrayWeatherInfoFromData(String str) {

		Type listType = new TypeToken<ArrayList<WeatherInfo>>() {
		}.getType();

		return new Gson().fromJson(str, listType);
	}

	public void setForecast(ForecastEntity forecast) {
		this.forecast = forecast;
	}

	public void setRealtime(RealtimeEntity realtime) {
		this.realtime = realtime;
	}

	public void setAqi(AqiEntity aqi) {
		this.aqi = aqi;
	}

	public ForecastEntity getForecast() {
		return forecast;
	}

	public RealtimeEntity getRealtime() {
		return realtime;
	}

	public AqiEntity getAqi() {
		return aqi;
	}

	public static class ForecastEntity {
		private String city;
		private String city_en;
		private String cityid;
		private String date_y;
		private String temp1;
		private String temp2;
		private String temp3;
		private String temp4;
		private String temp5;
		private String temp6;
		private String weather1;
		private String weather2;
		private String weather3;
		private String weather4;
		private String weather5;
		private String weather6;
		private String wind1;
		private String wind2;
		private String wind3;
		private String wind4;
		private String wind5;
		private String wind6;

		public ForecastEntity(String city, String city_en, String cityid, String date_y, String temp1, String temp2, String temp3, String temp4, String temp5, String temp6, String weather1, String weather2, String weather3, String weather4, String weather5, String weather6, String wind1, String wind2, String wind3, String wind4, String wind5, String wind6) {
			this.city = city;
			this.city_en = city_en;
			this.cityid = cityid;
			this.date_y = date_y;
			this.temp1 = temp1;
			this.temp2 = temp2;
			this.temp3 = temp3;
			this.temp4 = temp4;
			this.temp5 = temp5;
			this.temp6 = temp6;
			this.weather1 = weather1;
			this.weather2 = weather2;
			this.weather3 = weather3;
			this.weather4 = weather4;
			this.weather5 = weather5;
			this.weather6 = weather6;
			this.wind1 = wind1;
			this.wind2 = wind2;
			this.wind3 = wind3;
			this.wind4 = wind4;
			this.wind5 = wind5;
			this.wind6 = wind6;
		}

		public static ForecastEntity objectFromData(String str) {

			return new Gson().fromJson(str, ForecastEntity.class);
		}

		public static List<ForecastEntity> arrayForecastEntityFromData(String str) {

			Type listType = new TypeToken<ArrayList<ForecastEntity>>() {
			}.getType();

			return new Gson().fromJson(str, listType);
		}

		public void setCity(String city) {
			this.city = city;
		}

		public void setCity_en(String city_en) {
			this.city_en = city_en;
		}

		public void setCityid(String cityid) {
			this.cityid = cityid;
		}

		public void setDate_y(String date_y) {
			this.date_y = date_y;
		}

		public void setTemp1(String temp1) {
			this.temp1 = temp1;
		}

		public void setTemp2(String temp2) {
			this.temp2 = temp2;
		}

		public void setTemp3(String temp3) {
			this.temp3 = temp3;
		}

		public void setTemp4(String temp4) {
			this.temp4 = temp4;
		}

		public void setTemp5(String temp5) {
			this.temp5 = temp5;
		}

		public void setTemp6(String temp6) {
			this.temp6 = temp6;
		}

		public void setWeather1(String weather1) {
			this.weather1 = weather1;
		}

		public void setWeather2(String weather2) {
			this.weather2 = weather2;
		}

		public void setWeather3(String weather3) {
			this.weather3 = weather3;
		}

		public void setWeather4(String weather4) {
			this.weather4 = weather4;
		}

		public void setWeather5(String weather5) {
			this.weather5 = weather5;
		}

		public void setWeather6(String weather6) {
			this.weather6 = weather6;
		}

		public void setWind1(String wind1) {
			this.wind1 = wind1;
		}

		public void setWind2(String wind2) {
			this.wind2 = wind2;
		}

		public void setWind3(String wind3) {
			this.wind3 = wind3;
		}

		public void setWind4(String wind4) {
			this.wind4 = wind4;
		}

		public void setWind5(String wind5) {
			this.wind5 = wind5;
		}

		public void setWind6(String wind6) {
			this.wind6 = wind6;
		}

		public String getCity() {
			return city;
		}

		public String getCity_en() {
			return city_en;
		}

		public String getCityid() {
			return cityid;
		}

		public String getDate_y() {
			return date_y;
		}

		public String getTemp1() {
			return temp1;
		}

		public String getTemp2() {
			return temp2;
		}

		public String getTemp3() {
			return temp3;
		}

		public String getTemp4() {
			return temp4;
		}

		public String getTemp5() {
			return temp5;
		}

		public String getTemp6() {
			return temp6;
		}

		public String getWeather1() {
			return weather1;
		}

		public String getWeather2() {
			return weather2;
		}

		public String getWeather3() {
			return weather3;
		}

		public String getWeather4() {
			return weather4;
		}

		public String getWeather5() {
			return weather5;
		}

		public String getWeather6() {
			return weather6;
		}

		public String getWind1() {
			return wind1;
		}

		public String getWind2() {
			return wind2;
		}

		public String getWind3() {
			return wind3;
		}

		public String getWind4() {
			return wind4;
		}

		public String getWind5() {
			return wind5;
		}

		public String getWind6() {
			return wind6;
		}
	}

	public static class RealtimeEntity {
		private String SD;
		private String WD;
		private String WS;
		private String temp;
		private String time;
		private String weather;

		public RealtimeEntity(String SD, String temp, String time, String WD, String weather, String WS) {
			this.SD = SD;
			this.temp = temp;
			this.time = time;
			this.WD = WD;
			this.weather = weather;
			this.WS = WS;
		}

		public static RealtimeEntity objectFromData(String str) {

			return new Gson().fromJson(str, RealtimeEntity.class);
		}

		public static List<RealtimeEntity> arrayRealtimeEntityFromData(String str) {

			Type listType = new TypeToken<ArrayList<RealtimeEntity>>() {
			}.getType();

			return new Gson().fromJson(str, listType);
		}

		public void setSD(String SD) {
			this.SD = SD;
		}

		public void setWD(String WD) {
			this.WD = WD;
		}

		public void setWS(String WS) {
			this.WS = WS;
		}

		public void setTemp(String temp) {
			this.temp = temp;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public void setWeather(String weather) {
			this.weather = weather;
		}

		public String getSD() {
			return SD;
		}

		public String getWD() {
			return WD;
		}

		public String getWS() {
			return WS;
		}

		public String getTemp() {
			return temp;
		}

		public String getTime() {
			return time;
		}

		public String getWeather() {
			return weather;
		}
	}

	public static class AqiEntity {
		private String pub_time;
		private String aqi;
		private String pm25;
		private String src;

		public AqiEntity(String aqi, String pm25, String pub_time, String src) {
			this.aqi = aqi;
			this.pm25 = pm25;
			this.pub_time = pub_time;
			this.src = src;
		}

		public static AqiEntity objectFromData(String str) {

			return new Gson().fromJson(str, AqiEntity.class);
		}

		public static List<AqiEntity> arrayAqiEntityFromData(String str) {

			Type listType = new TypeToken<ArrayList<AqiEntity>>() {
			}.getType();

			return new Gson().fromJson(str, listType);
		}

		public void setPub_time(String pub_time) {
			this.pub_time = pub_time;
		}

		public void setAqi(String aqi) {
			this.aqi = aqi;
		}

		public void setPm25(String pm25) {
			this.pm25 = pm25;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public String getPub_time() {
			return pub_time;
		}

		public String getAqi() {
			return aqi;
		}

		public String getPm25() {
			return pm25;
		}

		public String getSrc() {
			return src;
		}
	}
}
