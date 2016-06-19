package com.fec.me.meweather.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
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
import com.fec.me.meweather.util.Connectivity;
import com.fec.me.meweather.util.HttpCallbackListener;
import com.fec.me.meweather.util.HttpUtil;
import com.fec.me.meweather.util.Utility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private static final String CITIES_MAIN = "cities";
	private static final int NIGHT_BEGIN = 18;	// PM 6
	private static final int NIGHT_END = 6;			// AM 6

	private ListView navDrawer;
	private DrawerLayout drawerLayout;
	private android.support.v7.widget.Toolbar toolbar;

	private TextView mainTV_update;
	private TextView mainTV_TEMP;
	private TextView mainTV_PM;
	private TextView mainTV_HUMI;
	private TextView mainTV_WD;

	private NavAdapter navAdapter;
	private List<NavItem> navItemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String [] codeAndName = getIntent().getStringArrayExtra("CodeAndName");

		initUnits();
		setToolbar();
		setNavDrawer();
		if (codeAndName != null) {
			refreshNavDrawer(codeAndName);
		}

		changeBackgroundByTime();

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
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawerLayout.openDrawer(navDrawer);
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
		toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);

		navDrawer = (ListView) findViewById(R.id.id_nav_drawer);
		drawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);

		mainTV_TEMP = (TextView) findViewById(R.id.main_tv_temp);
		mainTV_PM = (TextView) findViewById(R.id.main_tv_pm25);
		mainTV_HUMI = (TextView) findViewById(R.id.main_tv_humi);
		mainTV_WD = (TextView) findViewById(R.id.main_tv_wd);
		mainTV_update = (TextView) findViewById(R.id.update_time);
	}

	private void setNavDrawer() {
		navItemList = new ArrayList<NavItem>();
		navAdapter = new NavAdapter(this, R.layout.nav_item, navItemList);
		navDrawer.setAdapter(navAdapter);

		navDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				drawerLayout.closeDrawer(navDrawer);
				getWeatherByNavItem(position);
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
//			showWeather();
			queryFromServer(url, cityCode);
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
		toolbar.setTitleTextColor(Color.WHITE);
		toolbar.setTitleTextAppearance(this, R.style.toolbar);
		mainTV_TEMP.setText(preferences.getString("temp","") + "℃");
		mainTV_PM.setText("PM2.5 : "+preferences.getString("pm25",""));
		mainTV_HUMI.setText("HUMI : "+preferences.getString("SD",""));
		mainTV_WD.setText(preferences.getString("WD","")+" : "+preferences.getString("WS",""));
		mainTV_update.setText("更新时间 :"+preferences.getString("pub_time",""));
	}
}
