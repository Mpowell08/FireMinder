package com.fireminder.fireminder;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class LaunchNotification extends Activity {

	static final int uniqueID = 12028;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		// Get variables from intent
		Bundle extras = getIntent().getExtras();
		String title = extras.getString("title");
		String body = extras.getString("body");
		String dest = extras.getString("destination");
		long arrivalTime = extras.getLong("arrival_time");
		long deptTime = extras.getLong("dept_time");
		
		// Set and launch notification
		Intent intent = new Intent(this, CancelAlarm.class);
		intent.putExtra("destination", dest);
		intent.putExtra("arrival_time", arrivalTime);
		intent.putExtra("dept_time", deptTime);
		intent.putExtra("title", title);
		intent.putExtra("body", body);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
		Notification n = new NotificationCompat.Builder(this).setContentText(body)
		.setContentIntent(pi)
		.setContentTitle(title)
		.setSmallIcon(R.drawable.izzet_tiny)
		.setDefaults(Notification.DEFAULT_ALL)
		.build();
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(uniqueID, n);
		
		
		
		// Shut up
		finish();
	}



}
