package com.example.museumclient;

import java.util.ArrayList;
import java.util.HashMap;

import util.Httputil;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; //閻€劍娼垫稉瀣祰閸ュ墽澧栭惃鍕閿涘苯鎮楅棃銏℃箒娴犲绮�
    
    public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // 閺嶅洭顣�
        TextView artist = (TextView)vi.findViewById(R.id.artist); // 濮濆本澧滈崥锟�
        TextView duration = (TextView)vi.findViewById(R.id.duration); // 閺冨爼鏆�
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // 缂傗晝鏆愰崶锟�
        
        HashMap<String, String> song = new HashMap<String, String>();
        song = data.get(position);
        
        // 鐠佸墽鐤哃istView閻ㄥ嫮娴夐崗鍐诧拷锟�
        title.setText(song.get(MainActivity.KEY_TITLE));
        artist.setText(song.get(MainActivity.KEY_ARTIST));
        duration.setText(song.get(MainActivity.KEY_DURATION));
        String url = Httputil.BASE_URL+song.get(MainActivity.KEY_THUMB_URL);
        imageLoader.DisplayImage(url, thumb_image);
        
		
        return vi;
    }
     
}
