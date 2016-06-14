package com.fec.me.meweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MeWeatherOpenHelper extends SQLiteOpenHelper {
	/**
	 * City表建表语句
	 */
	public static final String CREATE_CITY =
			"CREATE TABLE City (id INTEGER PRIMARY KEY AUTOINCREMENT, city_code TEXT, city_namecn TEXT, city_nameen TEXT, city_parent TEXT)";

	public MeWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_CITY); //创建City表
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
