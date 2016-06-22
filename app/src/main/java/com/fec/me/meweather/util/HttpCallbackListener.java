package com.fec.me.meweather.util;

public interface HttpCallbackListener {
	void onFinish(String response);
	void onError(Exception e);
}
