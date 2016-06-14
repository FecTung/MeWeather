package com.fec.me.meweather.util;

import java.io.InputStream;

public interface HttpCallbackListener {
	void onFinish(String response);
	void onError(Exception e);
}
