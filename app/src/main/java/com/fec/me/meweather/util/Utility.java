package com.fec.me.meweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.preference.PreferenceManager;

import com.fec.me.meweather.model.City;
import com.fec.me.meweather.model.MeWeatherDB;
import com.fec.me.meweather.model.WeatherInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

public class Utility{

	/**
	 * 解析处理城市数据
	 */
	public static boolean handleCityInfo(Context context, MeWeatherDB meWeatherDB){
		AssetManager manager;
		manager = context.getAssets();
		InputStream is = null;
		try {
			is = manager.open("cities.json", AssetManager.ACCESS_RANDOM);
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			JsonParser parser = new JsonParser();
			JsonObject rootObject = (JsonObject) parser.parse(isr);
			JsonArray cities = rootObject.get("cities").getAsJsonArray();
			for (JsonElement element : cities){
				JsonObject object = element.getAsJsonObject();
				City city = new City();
				city.setCityCode(object.get("code").getAsString());
				city.setCityNameCN(object.get("namecn").getAsString());
				city.setCityNameEN(object.get("nameen").getAsString());
				city.setCityParent(object.get("parent").getAsString());
				meWeatherDB.saveCity(city);
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}



	public static void handleWeatherInfo(Context context, String response){

		JsonParser parser = new JsonParser();
		JsonObject rootObject = (JsonObject) parser.parse(response);
		JsonObject forecast = rootObject.get("forecast").getAsJsonObject();
		JsonObject realtime = rootObject.get("realtime").getAsJsonObject();
		JsonObject aqi = rootObject.get("aqi").getAsJsonObject();

		WeatherInfo.ForecastEntity forecastEntity = new WeatherInfo.ForecastEntity(
				forecast.get("city").getAsString(), forecast.get("city_en").getAsString(),
				forecast.get("cityid").getAsString(), forecast.get("date_y").getAsString(),
				forecast.get("temp1").getAsString(),forecast.get("temp2").getAsString(),
				forecast.get("temp3").getAsString(),forecast.get("temp4").getAsString(),
				forecast.get("temp5").getAsString(),forecast.get("temp6").getAsString(),
				forecast.get("weather1").getAsString(),forecast.get("weather2").getAsString(),
				forecast.get("weather3").getAsString(),forecast.get("weather4").getAsString(),
				forecast.get("weather5").getAsString(),forecast.get("weather6").getAsString(),
				forecast.get("wind1").getAsString(),forecast.get("wind2").getAsString(),
				forecast.get("wind3").getAsString(),forecast.get("wind4").getAsString(),
				forecast.get("wind5").getAsString(),forecast.get("wind6").getAsString()
		);
		WeatherInfo.RealtimeEntity realtimeEntity = new WeatherInfo.RealtimeEntity(
				realtime.get("SD").getAsString(),realtime.get("temp").getAsString(),
				realtime.get("time").getAsString(),realtime.get("WD").getAsString(),
				realtime.get("weather").getAsString(),realtime.get("WS").getAsString()
		);
		WeatherInfo.AqiEntity aqiEntity = new WeatherInfo.AqiEntity(
				aqi.get("aqi").getAsString(),aqi.get("pm25").getAsString(),
				aqi.get("pub_time").getAsString(),aqi.get("src").getAsString()
		);

		WeatherInfo info = new WeatherInfo(aqiEntity,forecastEntity,realtimeEntity);

		saveWeatherInfo(context, info);

	}

	private static void saveWeatherInfo(Context context, WeatherInfo info) {

		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

		editor.putString("city", info.getForecast().getCity());
		editor.putString("city_en", info.getForecast().getCity_en());
		editor.putString("cityid", info.getForecast().getCityid());
		editor.putString("date_y",info.getForecast().getDate_y());
		editor.putString("temp1",info.getForecast().getTemp1());
		editor.putString("temp2",info.getForecast().getTemp2());
		editor.putString("temp3",info.getForecast().getTemp3());
		editor.putString("temp4",info.getForecast().getTemp4());
		editor.putString("temp5",info.getForecast().getTemp5());
		editor.putString("temp6",info.getForecast().getTemp6());
		editor.putString("weather1",info.getForecast().getWeather1());
		editor.putString("weather2",info.getForecast().getWeather2());
		editor.putString("weather3",info.getForecast().getWeather3());
		editor.putString("weather4",info.getForecast().getWeather4());
		editor.putString("weather5",info.getForecast().getWeather5());
		editor.putString("weather6",info.getForecast().getWeather6());
		editor.putString("wind1",info.getForecast().getWind1());
		editor.putString("wind2",info.getForecast().getWind2());
		editor.putString("wind3",info.getForecast().getWind3());
		editor.putString("wind4",info.getForecast().getWind4());
		editor.putString("wind5",info.getForecast().getWind5());
		editor.putString("wind6",info.getForecast().getWind6());

		editor.putString("SD",info.getRealtime().getSD());
		editor.putString("WD",info.getRealtime().getWD());
		editor.putString("WS",info.getRealtime().getWS());
		editor.putString("temp",info.getRealtime().getTemp());
		editor.putString("time",info.getRealtime().getTime());
		editor.putString("weather",info.getRealtime().getWeather());

		editor.putString("pub_time", info.getAqi().getPub_time());
		editor.putString("aqi", info.getAqi().getAqi());
		editor.putString("pm25", info.getAqi().getPm25());
		editor.putString("src", info.getAqi().getSrc());

		editor.apply();
	}

	public static String[] getDateByOrigin(){
		int [] dates = new int[]{};
		String [] dateList = new String[]{};
		Calendar calendar = Calendar.getInstance();
		int todayMark = calendar.get(Calendar.DAY_OF_WEEK);
		dates[0] = todayMark;
		for(int i = 1; i < 7; ++i){
			if ((dates[0] + i) <= 7) {
				dates[i] = dates[0] + i;
			} else {
				dates[i] = dates[0] + i - 7;
			}
		}
		for (int i = 0; i < dates.length; ++i){
			switch (dates[i]){
				case Calendar.SUNDAY:
					dateList[i] = "星期天";
					break;
				case Calendar.MONDAY:
					dateList[i] = "星期一";
					break;
				case Calendar.TUESDAY:
					dateList[i] = "星期二";
					break;
				case Calendar.WEDNESDAY:
					dateList[i] = "星期三";
					break;
				case Calendar.THURSDAY:
					dateList[i] = "星期四";
					break;
				case Calendar.FRIDAY:
					dateList[i] = "星期五";
					break;
				case Calendar.SATURDAY:
					dateList[i] = "星期六";
					break;
			}
		}
		return dateList;
	}
}