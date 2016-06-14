package com.fec.me.meweather.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fec.me.meweather.db.MeWeatherOpenHelper;
import com.fec.me.meweather.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class MeWeatherDB {
	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "me_weather";
	/**
	 * 数据库版本
	 */
	public static final  int VERSION = 1;

	private static MeWeatherDB meWeatherDB;
	private SQLiteDatabase db;
	/**
	 * 私有化构造方法
	 */
	private MeWeatherDB(Context context) {
		MeWeatherOpenHelper dbHelper = new MeWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 获取MeWeatherDB实例
	 */
	public synchronized static MeWeatherDB getInstance(Context context){
		if (meWeatherDB == null){
			meWeatherDB = new MeWeatherDB(context);
		}
		return meWeatherDB;
	}

	/**
	 * 将City实例存储到数据库
	 */
	public void saveCity(City city){
		if (city!=null){
			ContentValues values = new ContentValues();
			values.put("city_code", city.getCityCode());
			values.put("city_namecn", city.getCityNameCN());
			values.put("city_nameen", city.getCityNameEN());
			values.put("city_parent", city.getCityParent());
			db.insert("City", null, values);
		}
	}

	public List<City> findCity(Context context, String name){
		List<City> cityList = new ArrayList<City>();
		City city;
		Cursor cursor = db.query("City",
				null,
				"city_namecn" + "=?" + " OR " +
				"city_nameen" + "=?" + " OR " +
				"city_parent" + "=?",
				new String[]{name,name,name},
				null, null, null);
		cityList.clear();
		if (cursor.moveToFirst()){
			do {
				city  =new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setCityNameCN(cursor.getString(cursor.getColumnIndex("city_namecn")));
				city.setCityNameEN(cursor.getString(cursor.getColumnIndex("city_nameen")));
				city.setCityParent(cursor.getString(cursor.getColumnIndex("city_parent")));
				cityList.add(city);
			} while (cursor.moveToNext());
		}
		cursor.close();
		return cityList;
	}

	public boolean isDBEmpty(){
		Cursor mCursor = db.rawQuery("SELECT * FROM City", null);
		boolean isEmpty= mCursor.getCount()>10 ? false:true;
		mCursor.close();
		return isEmpty;
	}

	public void initDB(final Context context){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Utility.handleCityInfo(context, meWeatherDB);
			}
		}).start();
	}
}
