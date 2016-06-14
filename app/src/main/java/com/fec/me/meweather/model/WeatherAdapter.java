package com.fec.me.meweather.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fec.me.meweather.R;

import java.util.List;

public class WeatherAdapter extends ArrayAdapter<WeatherItem>{

	private int resourceId;

	public WeatherAdapter(Context context, int textViewResourceId, List<WeatherItem> objects) {
		super(context,textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		WeatherItem item = getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.date = (TextView) view.findViewById(R.id.id_tv_date);
			viewHolder.weather = (TextView) view.findViewById(R.id.id_tv_weather);
			viewHolder.temp = (TextView) view.findViewById(R.id.id_tv_temp);
			viewHolder.wind = (TextView) view.findViewById(R.id.id_tv_wind);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.date.setText(item.getDate());
		viewHolder.weather.setText(item.getWeather());
		viewHolder.temp.setText(item.getTemp());
		viewHolder.wind.setText(item.getWind());

		return view;
	}

	class ViewHolder{
		TextView date;
		TextView weather;
		TextView temp;
		TextView wind;
	}
}
