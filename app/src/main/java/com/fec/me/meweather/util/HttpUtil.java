package com.fec.me.meweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection conn = null;
				try {
					URL url = new URL(address);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(10000);
					conn.setReadTimeout(10000);

					InputStream is = conn.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					StringBuilder response = new StringBuilder();
					String line = null;
					while ((line = br.readLine()) != null) {
						response.append(line);
					}
					if (listener != null) {
						listener.onFinish(response.toString());
					}
				} catch (Exception e){
					if (listener != null){
						listener.onError(e);
					}
				} finally {
					if(conn != null){
						conn.disconnect();
					}
				}
			}
		}).start();
	}
}
