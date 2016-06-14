package com.fec.me.meweather.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fec.me.meweather.R;

import java.util.List;

public class NavAdapter extends ArrayAdapter<NavItem>{

	private int resourceId;

	public NavAdapter(Context context, int textViewResourceId, List<NavItem> objects) {
		super(context,textViewResourceId, objects);
		resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		NavItem item = getItem(position);
		View view;
		ViewHolder viewHolder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.navItemTV = (TextView) view.findViewById(R.id.id_tv_nav_item);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.navItemTV.setText(item.getCityName());

		return view;
	}

	class ViewHolder{
		TextView navItemTV;
	}
}
