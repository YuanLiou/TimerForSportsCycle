package com.ray.timerforsportcycle;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.widget.Toast;

public class CountTimer extends Service{
	public static final String ACTION = "com.ray.timerforsportcycle.CountTimer";
	long timeInterval = 0;
	boolean isPaused = true;
	Handler handler;
	TimerTask task;
	Timer timer;
	@Override
	public void onCreate() {
		System.out.println("Mode: onCreate.");
		handler = new Handler();
		timer = new Timer();
		super.onCreate();
	}

	@Override
	public IBinder onBind(Intent intent) {
		System.out.println("Mode: onBind.");
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			int time = bundle.getInt("Time");
			if (time > 0) {
				//時間是正值，開始倒數計時
				Toast.makeText(this, R.string.service_start, Toast.LENGTH_LONG).show();
				timeInterval = time * 1000;
				//timeInterval = time * 1000 * 60;   //分種轉換成秒
			}
			System.out.println("Mode: OnStartCommand");
			if (isPaused) {
				if (task == null) {
					task = new TimerTask() {
						@Override
						public void run() {
							remind();
						}
					};
				}
				timer.schedule(task, 0, timeInterval);
				isPaused = false;
			}
			
		}
		return super.onStartCommand(intent, flags, startId);
	}	
	
	void remind() {
		Vibrator vibrator = (Vibrator) getApplication().getSystemService(Service.VIBRATOR_SERVICE);
		vibrator.vibrate(500);    //震動半秒鐘
	}

	@Override
	public boolean onUnbind(Intent intent) {
	    System.out.println("Mode: onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isPaused = true;
		task = null;
		timer.cancel();
		System.out.println("Mode: onDestory");
		Toast.makeText(CountTimer.this, R.string.service_stop, Toast.LENGTH_SHORT).show();
	}

}
