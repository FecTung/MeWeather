package com.fec.me.meweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fec.me.meweather.R;
import com.fec.me.meweather.model.NavAdapter;
import com.fec.me.meweather.model.NavItem;
import com.fec.me.meweather.model.WeatherAdapter;
import com.fec.me.meweather.model.WeatherItem;
import com.fec.me.meweather.util.Connectivity;
import com.fec.me.meweather.util.HttpCallbackListener;
import com.fec.me.meweather.util.HttpUtil;
import com.fec.me.meweather.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private ListView navDrawer;
	private ListView weatherLV;
	private DrawerLayout drawerLayout;
	private TextView navAddCity;

	private NavAdapter navAdapter;
	private List<NavItem> navItemList;
	private WeatherAdapter weatherAdapter;
	private List<WeatherItem> weatherItemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String [] codeAndName = getIntent().getStringArrayExtra("CodeAndName");

		initUnits();
		setDrawerLayout();
		setNavDrawer();
		if (codeAndName != null) {
			refreshNavDrawer(codeAndName);
		}
		navDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				drawerLayout.closeDrawer(navDrawer);
				getWeatherByNavItem(position);
			}
		});
	}

	private void refreshNavDrawer(String[] codeAndName) {
		NavItem item = new NavItem(codeAndName[0], codeAndName[1]);
		navItemList.add(item);
		navAdapter.notifyDataSetChanged();
		queryWeatherInfo(codeAndName[0]);
	}

	private void initUnits() {
		navDrawer = (ListView) findViewById(R.id.id_nav_drawer);
		weatherLV = (ListView) findViewById(R.id.id_lv_weather);
		drawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
		navAddCity = (TextView) findViewById(R.id.id_nav_footer);
	}

	private void setDrawerLayout() {
		weatherItemList = new ArrayList<WeatherItem>();
		weatherAdapter = new WeatherAdapter(this, R.layout.weather_item, weatherItemList);
		weatherLV.setAdapter(weatherAdapter);
	}

	private void setNavDrawer() {
		navItemList = new ArrayList<NavItem>();
		navAdapter = new NavAdapter(this, R.layout.nav_item, navItemList);
		navDrawer.setAdapter(navAdapter);

		navAddCity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ChooseCityActivity.class);
				startActivity(intent);
			}
		});
	}

	private void getWeatherByNavItem(int position) {
		NavItem item = navItemList.get(position);
		String cityCode = item.getCityCode();
		queryWeatherInfo(cityCode);
	}

	private void queryWeatherInfo(String cityCode) {
		String url = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=" + cityCode;
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		NetworkInfo networkInfo = Connectivity.getNetworkInfo(MainActivity.this);
		if (!networkInfo.isConnected() && cityCode.equals(preferences.getString("cityid", ""))){
			showWeather();
		}else if(networkInfo.isConnected()){
			queryFromServer(url, cityCode);
		}else{
			Toast.makeText(MainActivity.this, "无网络连接", Toast.LENGTH_SHORT);
		}
	}

	private void queryFromServer(final String address, final String cityCode) {
		if (!TextUtils.isEmpty(address)) {
			HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
				@Override
				public void onFinish(String response) {
					if (!TextUtils.isEmpty(cityCode)) {
						Utility.handleWeatherInfo(MainActivity.this, response);
						SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
						String [] dateList = Utility.getDateByOrigin();

						WeatherItem item0 = new WeatherItem(dateList[0], preferences.getString("temp",""), preferences.getString("weather",""), preferences.getString("WD",""));
						WeatherItem item1 = new WeatherItem(dateList[1], preferences.getString("temp1",""), preferences.getString("weather1",""), preferences.getString("wind1",""));
						WeatherItem item2 = new WeatherItem(dateList[2], preferences.getString("temp2",""), preferences.getString("weather2",""), preferences.getString("wind2",""));
						WeatherItem item3 = new WeatherItem(dateList[3], preferences.getString("temp3",""), preferences.getString("weather3",""), preferences.getString("wind3",""));
						WeatherItem item4 = new WeatherItem(dateList[4], preferences.getString("temp4",""), preferences.getString("weather4",""), preferences.getString("wind4",""));
						WeatherItem item5 = new WeatherItem(dateList[5], preferences.getString("temp5",""), preferences.getString("weather5",""), preferences.getString("wind5",""));
						WeatherItem item6 = new WeatherItem(dateList[6], preferences.getString("temp6",""), preferences.getString("weather6",""), preferences.getString("wind6",""));
						weatherItemList.add(item0);
						weatherItemList.add(item1);
						weatherItemList.add(item2);
						weatherItemList.add(item3);
						weatherItemList.add(item4);
						weatherItemList.add(item5);
						weatherItemList.add(item6);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								showWeather();
							}
						});
					}
				}
				@Override
				public void onError(Exception e) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(MainActivity.this, "天气信息获取失败！", Toast.LENGTH_SHORT);
						}
					});
				}
			});
		}
	}

	private void showWeather() {
		weatherAdapter.notifyDataSetChanged();
	}
}
