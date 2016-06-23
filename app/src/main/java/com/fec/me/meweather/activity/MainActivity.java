package com.fec.me.meweather.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fec.me.meweather.R;
import com.fec.me.meweather.util.HttpCallbackListener;
import com.fec.me.meweather.util.HttpUtil;
import com.fec.me.meweather.util.MenuUtils;
import com.fec.me.meweather.util.Utility;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private static final String MENU_CITIES = "city_menus";
	private static final int NIGHT_BEGIN = 18;	// PM 6
	private static final int NIGHT_END = 6;			// AM 6

	private NavigationView navDrawer;
	private DrawerLayout drawerLayout;
	private android.support.v7.widget.Toolbar toolbar;

	private TextView mainTV_update;
	private TextView mainTV_TEMP;
	private TextView mainTV_PM;
	private TextView mainTV_HUMI;
	private TextView mainTV_WD;
	private TextView addCity;

	private String [] codeAndName = null;
	private String cityCode = "101221702";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		codeAndName = getIntent().getStringArrayExtra("CodeAndName");


		initUnits();
		setToolbar();
		try {
			refreshNav(codeAndName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setListener();
		queryWeatherInfo(cityCode);
		changeBackgroundByTime();

	}

	@Override
	protected void onResume() {
		super.onResume();
		queryWeatherInfo(cityCode);
		changeBackgroundByTime();
	}

	private void setListener() {
		addCity.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ChooseCityActivity.class);
				startActivity(intent);
			}
		});
		navDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				MenuUtils menuUtils = new MenuUtils(MainActivity.this, MENU_CITIES);
				cityCode = menuUtils.findCodeByName((String) item.getTitle());
				queryWeatherInfo(cityCode);
				drawerLayout.closeDrawer(navDrawer);
				return false;
			}
		});

	}

	private void changeBackgroundByTime() {
		Calendar now = Calendar.getInstance();
		if (now.get(Calendar.HOUR_OF_DAY) < NIGHT_BEGIN && now.get(Calendar.HOUR_OF_DAY) > NIGHT_END){
			drawerLayout.setBackground(getDrawable(R.drawable.daytime));
		} else {
			drawerLayout.setBackground(getDrawable(R.drawable.night));
		}
	}

	private void setToolbar() {
		setSupportActionBar(toolbar);
		toolbar.setTitle("城市");
		toolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
		toolbar.setTitleTextColor(Color.WHITE);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawerLayout.openDrawer(navDrawer);
			}
		});
	}

	private void refreshNav(String[] codeAndName) throws IOException {
		MenuUtils menuUtils = new MenuUtils(this, MENU_CITIES);
		if (codeAndName != null){
			menuUtils.addMenuItem(codeAndName);
			cityCode = codeAndName[0];
		}
		List<String[]> menuList = menuUtils.readMenu();
		if (menuList != null) {
			for (String[] item : menuList) {
				navDrawer.getMenu().add(item[1]);
			}
		}
	}

	private void initUnits() {
		toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

		navDrawer = (NavigationView) findViewById(R.id.id_nav_drawer);
		drawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);

		mainTV_TEMP = (TextView) findViewById(R.id.main_tv_temp);
		mainTV_PM = (TextView) findViewById(R.id.main_tv_pm25);
		mainTV_HUMI = (TextView) findViewById(R.id.main_tv_humi);
		mainTV_WD = (TextView) findViewById(R.id.main_tv_wd);
		mainTV_update = (TextView) findViewById(R.id.update_time);

		addCity = (TextView) findViewById(R.id.add_city);
	}

	private void queryWeatherInfo(String cityCode) {
		String url = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=" + cityCode;

		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		if (networkInfo != null && networkInfo.isConnected()){
//			showWeather();
			queryFromServer(url, cityCode);
		}else{
			Snackbar.make(drawerLayout, "无网络连接！" , Snackbar.LENGTH_SHORT).setAction("OK", new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					return;
				}
			}).show();
		}
	}

	private void queryFromServer(final String address, final String cityCode) {
		if (!TextUtils.isEmpty(address)) {
			HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
				@Override
				public void onFinish(String response) {
					if (!TextUtils.isEmpty(cityCode)) {
						Utility.handleWeatherInfo(MainActivity.this, response);
							runOnUiThread(new Runnable() {
							@Override
							public void run() {
								refreshUI();
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

	private void refreshUI() {
		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

		toolbar.setTitle(preferences.getString("city","城市"));
		toolbar.setTitleTextAppearance(this, R.style.toolbar);
		mainTV_TEMP.setText(preferences.getString("temp","") + "℃");
		mainTV_PM.setText("PM2.5 : "+preferences.getString("pm25",""));
		mainTV_HUMI.setText("HUMI : "+preferences.getString("SD",""));
		mainTV_WD.setText(preferences.getString("WD","")+" : "+preferences.getString("WS",""));
		mainTV_update.setText("更新时间 :"+preferences.getString("pub_time",""));
	}
}
