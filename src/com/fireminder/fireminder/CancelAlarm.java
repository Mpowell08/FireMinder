package com.fireminder.fireminder;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.TextView;

import android.os.Bundle;
import android.app.NotificationManager;
import android.content.Context;

public class CancelAlarm extends Activity {

	static final int uniqueID = 12028;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cancel_alarm);
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(uniqueID);
		Bundle extras = getIntent().getExtras();
		String title = extras.getString("title");
		String body = extras.getString("body");
		String dest = extras.getString("destination");
		long arrivalTime = extras.getLong("arrival_time");
		long deptTime = extras.getLong("dept_time");
		
		TextView title_tv = (TextView) findViewById(R.id.title_tv);
		title_tv.setText(title);
		TextView body_tv = (TextView) findViewById(R.id.body_tv);
		body_tv.setText(body);
		
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		finish();
	}



}
