package com.example.museumclient;

import util.Httputil;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class help extends Activity {
	private Button button_yjian;
	private EditText edittext_yijian;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		button_yjian =(Button)findViewById(R.id.button_yijian);
		edittext_yijian =(EditText)findViewById(R.id.edittext_yijian);
		
	}
	public void button_jianyi(View vieww1){
		String yijian =(String)edittext_yijian.getText().toString();
		if(yijian !=null){
			try{
				String url =Httputil.BASE_URL+"servlet/YijianServlet?yijian=" + yijian;
				String result = Httputil.queryStringForPost(url);
				Toast.makeText(help.this, result, Toast.LENGTH_SHORT)
				.show();
			}catch(Exception e ){
				e.printStackTrace();
			}
			
		}
	}

}
