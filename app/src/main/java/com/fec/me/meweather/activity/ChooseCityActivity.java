package com.fec.me.meweather.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.fec.me.meweather.R;
import com.fec.me.meweather.model.City;
import com.fec.me.meweather.model.MeWeatherDB;

import java.util.ArrayList;
import java.util.List;

public class ChooseCityActivity extends AppCompatActivity {

	private Toolbar toolbar;
	private ListView lvShow;
	private EditText etSearch;
	private Button btnSearch;
	private String userInput;
	private List<City> cityList;

	private MeWeatherDB meWeatherDB;
	private ArrayAdapter<String> adapter;
	private List<String> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_city);

		initLayoutUnit();
		initToolbar();
		initDB();
		setListView();
		setListener();

	}

	/**
	 * 初始化Adapter + ListView
	 * 设置Adapter
	 */
	private void setListView() {
		list = new ArrayList<String>();
		userInput = null;
		cityList = new ArrayList<City>();

		adapter = new ArrayAdapter<String>(ChooseCityActivity.this, android.R.layout.simple_list_item_1, list);
		lvShow.setAdapter(adapter);
	}

	/**
	 * 设置若监听器的监听事件
	 */
	private void setListener() {
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				searchCity();
			}
		});
		lvShow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				City city = cityList.get(position);
				Intent intent = new Intent(ChooseCityActivity.this, MainActivity.class);
				intent.putExtra("CodeAndName", new String[]{city.getCityCode(),city.getCityNameCN()});
				startActivity(intent);
			}
		});
	}

	/**
	 * 初始化界面控件
	 */
	private void initLayoutUnit() {
		etSearch = (EditText) findViewById(R.id.et_search);
		btnSearch = (Button) findViewById(R.id.btn_search);
		lvShow = (ListView) findViewById(R.id.lv_show);
		btnSearch.setBackgroundResource(R.mipmap.ic_search_black_24dp);
	}

	/**
	 * 初始化Database
	 */
	private void initDB() {
		meWeatherDB = MeWeatherDB.getInstance(this);
		if (meWeatherDB.isDBEmpty()){
			meWeatherDB.initDB(ChooseCityActivity.this);
		}
	}

	/**
	 * 初始化Toolbar
	 */
	private void initToolbar() {
		toolbar = (Toolbar) findViewById(R.id.mToolbar);
		toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
		setSupportActionBar(toolbar);

		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ChooseCityActivity.this, MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	/**
	 * 根据用户输入搜索城市
	 */
	private void searchCity() {
		userInput = etSearch.getText().toString();
		if (!meWeatherDB.isDBEmpty()){
			cityList = meWeatherDB.findCity(ChooseCityActivity.this, userInput);
			list.clear();
			for (City city:cityList){
				list.add(city.getCityNameCN() + ", " + city.getCityParent());
			}
			adapter.notifyDataSetChanged();
		} else {
			meWeatherDB.initDB(ChooseCityActivity.this);
		}
	}

}