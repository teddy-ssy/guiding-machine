package com.example.museum_client;

import com.example.museumclient.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchDetial extends Activity {
	private ListView resultlist;
	private String result, num;
	private int number;
	private String[] name, id;
	private Intent intent;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_search);
		intent = getIntent();
		result = (String) intent.getSerializableExtra("result");

		// text_result = (TextView) findViewById(R.id.text_result);
		// text_result.setText(result);

		String[] msgs = result.split(";");
		int idx = msgs[0].indexOf("=");
		num = msgs[0].substring(idx + 1);
		number = Integer.parseInt(num);
		id = new String[number];
		name = new String[number];
		for (int i = 0; i < number; i++) {
			idx = msgs[i + 1].indexOf("=");
			name[i] = msgs[i + 1].substring(idx + 1);
			idx = msgs[i + number + 1].indexOf("=");
			id[i] = msgs[number + 1 + i].substring(idx + 1);
		}
		resultlist = (ListView) findViewById(R.id.listview_search);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
				R.layout.list_detial, name);
		resultlist.setAdapter(adapter1);
		resultlist.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String text = id[arg2];
				Intent intent = new Intent(SearchDetial.this, Detial.class);
				Bundle bundle = new Bundle();
				bundle.putString("result", text);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});

	}
}
