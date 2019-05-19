package com.example.museumclient;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import util.Httputil;

import com.example.museum_client.Detial;
import com.example.museum_client.SearchDetial;
import com.example.zxing.CaptureActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewSwitcher.ViewFactory;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements ViewFactory,
		OnClickListener {
	public static MainActivity instance = null;
	private ViewPager mTabPager;
	private ImageView mTabImg;
	private ImageView mTab1, mTab2, mTab3, mTab4;
	private int zero = 0;// ����ͼƬƫ����
	private int currIndex = 0;// ��ǰҳ�����
	private int one;// ����ˮƽ����λ��
	private int two;
	private int three;
	public static String FirstFolder = "MuseumClient";// һ��Ŀ¼
	public static String SecondFolder = "ListViewImage";// ����Ŀ¼
	public static String ThirdFolder = "ThirdFolder";// ����Ŀ¼
	private final static String ALBUM_PATH = Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ FirstFolder
			+ File.separator;
	private final static String Second_PATH = ALBUM_PATH + SecondFolder
			+ File.separator;
	// private Button button_search;
	private EditText edittext_search;
	static final String URL = Httputil.BASE_URL + "source/Myxml/music.xml";
	static final String KEY_SONG = "song"; // parent node
	static final String KEY_ID = "id";
	static final String KEY_TITLE = "title";
	static final String KEY_ARTIST = "artist";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url";
	ImageSwitcher imageswitcher;
	private Button button_first;
	private Button button_second;
	private Button button_third;
	private Button button_b;
	static final Integer[] imagelist = { R.drawable.bceng, R.drawable.yiceng,
			R.drawable.erceng, R.drawable.sanceng };
	private static int index = 0;

	@TargetApi(Build.VERSION_CODES.BASE)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		instance = this;
		mTabPager = (ViewPager) findViewById(R.id.tabpager);
		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
		mTab1 = (ImageView) findViewById(R.id.img_weixin);
		mTab2 = (ImageView) findViewById(R.id.img_address);
		mTab3 = (ImageView) findViewById(R.id.img_friends);
		mTab4 = (ImageView) findViewById(R.id.img_settings);
		mTabImg = (ImageView) findViewById(R.id.img_tab_now);
		mTab1.setOnClickListener(new MyOnClickListener(0));
		mTab2.setOnClickListener(new MyOnClickListener(1));
		mTab3.setOnClickListener(new MyOnClickListener(2));
		mTab4.setOnClickListener(new MyOnClickListener(3));
		// button_search = (Button)findViewById(R.id.button_search);
		// button_search.setOnClickListener(new SearchClickListener());
		Display currDisplay = getWindowManager().getDefaultDisplay();// ��ȡ��Ļ��ǰ�ֱ���
		int displayWidth = currDisplay.getWidth();
		int displayHeight = currDisplay.getHeight();
		one = displayWidth / 4; // ����ˮƽ����ƽ�ƴ�С
		two = one * 2;
		three = one * 3;
		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.barcode, null);
		View view2 = mLi.inflate(R.layout.museummap, null);
		View view3 = mLi.inflate(R.layout.setting, null);
		View view4 = mLi.inflate(R.layout.settings, null);
		edittext_search = (EditText) view1.findViewById(R.id.editText_search);
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // �������ȡxml
		Document doc = parser.getDomElement(xml); // ��ȡ DOM �ڵ�

		NodeList nl = doc.getElementsByTagName(KEY_SONG);
		for (int i = 0; i < nl.getLength(); i++) {
			// �½�һ�� HashMap
			HashMap<String, String> map = new HashMap<String, String>();
			Element e = (Element) nl.item(i);
			// ÿ���ӽڵ���ӵ�HashMap�ؼ�= >ֵ
			map.put(KEY_ID, parser.getValue(e, KEY_ID));
			map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
			map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
			map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
			map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

			// HashList��ӵ������б�
			songsList.add(map);
		}

		ListView list = (ListView) view1.findViewById(R.id.list);
		LazyAdapter adapter = new LazyAdapter(this, songsList);
		mTabPager.setAdapter(mPagerAdapter);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				/*
				 * String url = Httputil.BASE_URL +
				 * "servlet/findItemServlet?id=" + String.valueOf(position);
				 * 
				 * String result = Httputil.queryStringForPost(url); if (result
				 * == null) { result = "�޵õ����";
				 */
				try {
					String result = String.valueOf(position + 1);
					Intent intent = new Intent(MainActivity.this, Detial.class);
					Bundle bundle = new Bundle();
					bundle.putString("result", result);
					intent.putExtras(bundle);
					startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		imageswitcher = (ImageSwitcher) view2.findViewById(R.id.imageswitcher);
		imageswitcher.setFactory(this);
		imageswitcher.setImageResource(imagelist[1]);
		imageswitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		imageswitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));

		button_b = (Button) view2
				.findViewById(com.example.museumclient.R.id.button_b);
		button_first = (Button) view2
				.findViewById(com.example.museumclient.R.id.button_first);
		button_second = (Button) view2
				.findViewById(com.example.museumclient.R.id.button_second);
		button_third = (Button) view2
				.findViewById(com.example.museumclient.R.id.button_third);

		button_b.setOnClickListener(this);
		button_first.setOnClickListener(this);
		button_second.setOnClickListener(this);
		button_third.setOnClickListener(this);

	}

	public void CreateLocalFolder() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
		if (sdCardExist) {
			File dirFirstFile = new File(ALBUM_PATH);// �½�һ����Ŀ¼
			if (!dirFirstFile.exists()) {// �ж��ļ���Ŀ¼�Ƿ����
				dirFirstFile.mkdir();// �������򴴽�
			}
			File dirSecondFile = new File(Second_PATH);// �½�������Ŀ¼
			if (!dirSecondFile.exists()) {// �ж��ļ���Ŀ¼�Ƿ����
				dirSecondFile.mkdir();// �������򴴽�

			}
		} else {
			Toast.makeText(MainActivity.this, "SDK卡错", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void btnmainright(View v) {
		Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
		startActivity(intent);
	}

	public void function(View v) {
		Intent intent = new Intent(MainActivity.this, function.class);
		startActivity(intent);
	}

	public void helpClick(View v) {
		Intent intent = new Intent(MainActivity.this, help.class);
		startActivity(intent);
	}

	public void Clearcache(View v) {
		FileCache filec = new FileCache(this);
		filec.clear();
		Toast.makeText(MainActivity.this, "缓存清除成功", Toast.LENGTH_SHORT).show();
	}

	public void buttonsearch(View view1) {
		String name = (String) edittext_search.getText().toString();
		if (name != null) {
			try {
				String url = Httputil.BASE_URL + "servlet/SearchServlet?name="
						+ name;
				String result = Httputil.queryStringForPost(url);
				if (result == null) {
					result = "没有结果";
				}
				Intent intent = new Intent(MainActivity.this,
						SearchDetial.class);
				Bundle bundle = new Bundle();
				bundle.putString("result", result);
				intent.putExtras(bundle);
				startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * public class SearchClickListener implements View.OnClickListener{ public
	 * SearchClickListener() { // TODO Auto-generated constructor stub }
	 * 
	 * public void onClick(View arg0) { String name =
	 * edittext_search.getText().toString(); if (name != null) { try { String
	 * url = Httputil.BASE_URL + "servlet/SearchServlet?name=" + name; String
	 * result = Httputil.queryStringForPost(url); Intent intent = new
	 * Intent(MainActivity.this, SearchDetial.class); Bundle bundle = new
	 * Bundle(); bundle.putString("result", result); intent.putExtras(bundle);
	 * startActivity(intent); } catch (Exception e) { e.printStackTrace(); } } }
	 * 
	 * }
	 */
	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			mTabPager.setCurrentItem(index);
		}
	};

	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				mTab1.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_weixin_pressed));
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_address_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_find_frd_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_settings_normal));
				}
				break;
			case 1:
				mTab2.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_address_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, one, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_weixin_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_find_frd_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_settings_normal));
				}
				break;
			case 2:
				mTab3.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_find_frd_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, two, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_weixin_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_address_normal));
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
					mTab4.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_settings_normal));
				}
				break;
			case 3:
				mTab4.setImageDrawable(getResources().getDrawable(
						R.drawable.tab_settings_pressed));
				if (currIndex == 0) {
					animation = new TranslateAnimation(zero, three, 0, 0);
					mTab1.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_weixin_normal));
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
					mTab2.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_address_normal));
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
					mTab3.setImageDrawable(getResources().getDrawable(
							R.drawable.tab_find_frd_normal));
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(150);
			mTabImg.startAnimation(animation);
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	}

	public View makeView() {
		// TODO Auto-generated method stub
		return new ImageView(this);
	}

	public void onClick(View v) { // ʵ��button��onClick����
		// TODO Auto-generated method stub
		if (v.getId() == R.id.button_b) {
			index = 3;
			imageswitcher.setImageResource(imagelist[index]);

		} else if (v.getId() == R.id.button_first) {
			index = 0;
			imageswitcher.setImageResource(imagelist[index]);
		} else if (v.getId() == R.id.button_second) {
			index = 1;
			imageswitcher.setImageResource(imagelist[index]);
		} else if (v.getId() == R.id.button_third) {
			index = 2;
			imageswitcher.setImageResource(imagelist[index]);
		}
		/*
		 * switch (v.getId()) {
		 * 
		 * case R.id.button_b: index = 3;
		 * imageswitcher.setImageResource(imagelist[index]); break;
		 * 
		 * case R.id.button_first: index = 0;
		 * imageswitcher.setImageResource(imagelist[index]); break;
		 * 
		 * case R.id.button_second: index = 1;
		 * imageswitcher.setImageResource(imagelist[index]); break;
		 * 
		 * case R.id.button_third: index = 2;
		 * imageswitcher.setImageResource(imagelist[index]); break;
		 * 
		 * default: break; }
		 */

	}
}
