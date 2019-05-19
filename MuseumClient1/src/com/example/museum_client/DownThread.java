package com.example.museum_client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import util.Httputil;

import android.net.Uri;
import android.os.Message;

public class DownThread implements Runnable {
	private String picsrt;
	public DownThread(String picsrt){
		this.picsrt = picsrt;
	}
	public void run() {
		String[]msgs = picsrt.split(";");
		int idx = msgs[0].indexOf("=");
		String num = msgs[0].substring(idx+1);
		int number = Integer.parseInt(num);
		String[] pics = new String[number];
		List datas = new ArrayList();
		for(int i=0;i<number;i++){
			idx = msgs[i+1].indexOf("=");
			pics[i]=msgs[i+1].substring(idx+1);
			HttpClient httpClient = new DefaultHttpClient();
			//Uri uri =Uri.parse(); 
			HttpGet httpGet = new HttpGet(Httputil.BASE_URL+pics[i]);
			HttpResponse httpResponse = null;
			try {
				httpResponse = httpClient.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					byte[] data = EntityUtils.toByteArray(httpResponse.getEntity());
					datas.add(data);
					/**/
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Message msg = Message.obtain();
		msg.obj = datas;
		msg.what = 3;
		Detial.handler.sendMessage(msg);
	}
}
