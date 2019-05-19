package com.example.museum_client;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import util.Httputil;

import com.example.museumclient.R;

import Entity.Mp3Info;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Detial extends Activity implements OnGestureListener {
	private LinearLayout layout;
	private int now = 0;
	private int pictureCounts = 5;
	private static ViewFlipper flipper;
	private GestureDetector gestureDetector;
	private TextView textview, textview1;
	private Intent intent;
	private String result;
	private Button button_zan;
	private static Button button_pause;
	private android.view.animation.Animation animation;
	static MediaPlayer media = null;
	private Timer timer;
	private static SeekBar seekbar;
	private static String image_path = Httputil.BASE_URL + "source/1.jpg";
	private static ProgressDialog dialog;
	private static List imageviews = new ArrayList();
	private static List datas = new ArrayList();
	private static Mp3Info music;
	private static int number;

	// �ؼ���
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detial);
		textview = (TextView) findViewById(R.id.text_detial);
		intent = getIntent();
		result = (String) intent.getSerializableExtra("result");
		textview.setText(result);
		button_zan = (Button) findViewById(R.id.button_zan);
		animation = AnimationUtils.loadAnimation(Detial.this, R.anim.nn);
		textview1 = (TextView) findViewById(R.id.tv_one);
		button_zan.setOnClickListener(zan);
		flipper = (ViewFlipper) findViewById(R.id.flipper);
		dialog = new ProgressDialog(this);
		dialog.setTitle("等一下ʾ");
		dialog.setMessage("正在拼命加载。。。");
		dialog.setCancelable(false);
		init();
	}

	// ��ť�ļ���
	OnClickListener zan = new OnClickListener() {
		public void onClick(View v) {
			if (v == button_zan) {
				textview1.setVisibility(View.VISIBLE);
				textview1.startAnimation(animation);
				new Handler().postDelayed(new Runnable() {
					public void run() {
						textview1.setVisibility(View.GONE);
					}
				}, 1000);
				String ex_id = result;
				String url = Httputil.BASE_URL + "servlet/MarkServlet?ex_id="
						+ ex_id;
				Httputil.queryStringForPost(url);
				button_zan.setBackgroundResource(R.drawable.like_t);
				button_zan.setEnabled(false);
			}
		}
	};
	OnClickListener pause = new OnClickListener() {
		public void onClick(View v) {
			if (v == button_pause) {

				if ("播放".equals(Detial.button_pause.getText().toString())) {
					try {
						Uri uri = Uri.parse(Httputil.BASE_URL + music.getStr());
						Detial.media = new MediaPlayer();
						Detial.media.setDataSource(Detial.this, uri);
						Detial.media
								.setAudioStreamType(AudioManager.STREAM_MUSIC);
						Detial.media
								.setOnCompletionListener(new MediaCompletionListener());
						Detial.media
								.setOnErrorListener(new MediaErrorListener());
						Detial.media.prepare();

						Detial.media.start();
						Detial.this.timer = new Timer();
						Detial.this.timer.schedule(new Task(), 0, 1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Detial.button_pause.setText("暂停");
				} else if ("继续"
						.equals(Detial.button_pause.getText().toString())) {
					Detial.media.start();
					Detial.button_pause.setText("暂停");
				} else if (Detial.media.isPlaying()) {
					Detial.media.pause();
					Detial.button_pause.setText("继续");
				}
			}
		}
	};

	// ��ʱ��
	private class Task extends TimerTask {
		@Override
		public void run() {
			Message message = new Message();
			message.what = 1;
			Detial.handler.sendMessage(message);
		}
	}

	// ���������
	protected void onDestroy() {
		super.onDestroy();

		if (Detial.media != null) {
			Detial.media.release();
			Detial.media = null;
		}
	}

	// handler
	@SuppressLint("Handlerleak")
	static Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				if (media != null) {
					int progress = Detial.media.getCurrentPosition();
					int alltime = Detial.media.getDuration();
					seekbar.setMax(alltime);
					Detial.seekbar.setProgress(progress);
				}
				break;
			case 2:
				break;
			case 3:
				datas = (List) msg.obj;
				for (int i = 0; i < number; i++) {
					byte[] data = (byte[]) datas.get(i);
					Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					ImageView imageview = (ImageView) imageviews.get(i);
					imageview.setImageBitmap(bmp);
					flipper.addView(imageview);
				}
				dialog.dismiss();
			}
			super.handleMessage(msg);
		}
	};

	// ���������
	private class SeekBarOnClickListenerImpl implements
			SeekBar.OnSeekBarChangeListener {
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			if (!fromUser)
				return;
			if (Detial.media != null) {
				Message message = new Message();
				Bundle b = new Bundle();
				b.putInt("p", progress);
				message.setData(b);
				message.what = 2;
				Detial.handler.sendMessage(message);
				Detial.media.seekTo(progress);
			}
		}

		public void onStartTrackingTouch(SeekBar arg0) {
		}

		public void onStopTrackingTouch(SeekBar arg0) {
		}
	}

	// �ؼ���ʼ��
	@SuppressWarnings("deprecation")
	private void init() {
		String ex_id = result;
		String url = Httputil.BASE_URL + "servlet/MusicServlet?ex_id=" + ex_id;
		String str = Httputil.queryStringForPost(url);
		url = Httputil.BASE_URL + "servlet/PicServlet?ex_id=" + ex_id;
		String picaddr = Httputil.queryStringForPost(url);
		url = Httputil.BASE_URL + "servlet/TextServlet?ex_id=" + ex_id;
		String text = Httputil.queryStringForPost(url);
		String[] msgs = picaddr.split(";");
		int idx = msgs[0].indexOf("=");
		String num = msgs[0].substring(idx + 1);
		number = Integer.parseInt(num);
		textview.setText(text);
		music = new Mp3Info();
		music.setex_id(Integer.parseInt(ex_id));
		music.setStr(str);
		for (int i = 0; i < number; i++) {
			ImageView imageview = new ImageView(this);
			imageviews.add(imageview);
		}
		gestureDetector = new GestureDetector(this);
		flipper.setDisplayedChild(now);
		layout = (LinearLayout) findViewById(R.id.linearLayout);
		Detial.button_pause = (Button) this.findViewById(R.id.button_music);
		Detial.seekbar = (SeekBar) this.findViewById(R.id.seekbar_music);
		Detial.button_pause.setOnClickListener(pause);
		Detial.seekbar
				.setOnSeekBarChangeListener(new SeekBarOnClickListenerImpl());
		generatePageControl();
		new Thread(new DownThread(picaddr)).start();
		dialog.show();
	}

	// ҳ�ŵ�ʵ��
	private void generatePageControl() {
		layout.removeAllViews();
		for (int i = 0; i < pictureCounts; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setPadding(0, 0, pictureCounts, 0);
			if (now == i) {
				imageView.setImageResource(R.drawable.page00);
			} else {
				imageView.setImageResource(R.drawable.page01);
			}
			this.layout.addView(imageView);
		}
	}

	// flipper�Ĺ�����д
	public boolean onDown(MotionEvent e) {
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e2.getX() - e1.getX() > 120) {
			Animation rInAnim = AnimationUtils.loadAnimation(Detial.this,
					R.anim.push_right_in);
			Animation rOutAnim = AnimationUtils.loadAnimation(Detial.this,
					R.anim.push_right_out);
			flipper.setInAnimation(rInAnim);
			flipper.setOutAnimation(rOutAnim);
			flipper.showPrevious();
			now--;
			if (now < 0) {
				now = pictureCounts - 1;
			}
			generatePageControl();
			return true;
		} else if (e2.getX() - e1.getX() < -120) {
			Animation lInAnim = AnimationUtils.loadAnimation(Detial.this,
					R.anim.push_left_in);
			Animation lOutAnim = AnimationUtils.loadAnimation(Detial.this,
					R.anim.push_left_out);
			flipper.setInAnimation(lInAnim);
			flipper.setOutAnimation(lOutAnim);
			flipper.showNext();
			now++;
			if (now > pictureCounts - 1) {
				now = 0;
			}
			generatePageControl();
			return true;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	public void onLongPress(MotionEvent e) {
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	public void onShowPress(MotionEvent e) {
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	// �������Ĵ�����д
	private class MediaErrorListener implements
			android.media.MediaPlayer.OnErrorListener {

		public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
			Detial.media.stop();
			Detial.media.release();
			Detial.media = null;
			return false;
		}
	}

	// ���������д
	private class MediaCompletionListener implements OnCompletionListener {

		public void onCompletion(MediaPlayer arg0) {
			Detial.media.stop();
			// Detial.this.media.release();
			Detial.media = null;
			Detial.button_pause.setText("����");
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

	if(keyCode == KeyEvent.KEYCODE_BACK) {
		flipper.removeAllViews();
	}
	 super.onKeyDown(keyCode, event);
	 return true;
	}
}

