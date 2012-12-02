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
		
		SQLiteDatabase database;
		AlarmDB alarmdb = new AlarmDB(this);
		String[] allColumns = {AlarmDB.COLUMN_ID, AlarmDB.COLUMN_DEST, 
				AlarmDB.COLUMN_ARRIVALTIME, AlarmDB.COLUMN_DEPTTIME};
		database = alarmdb.getWritableDatabase();
		
		// Get variables from intent
		Bundle extras = getIntent().getExtras();
		String title = extras.getString("title");
		String body = extras.getString("body");
		String dest = extras.getString("destination");
		long arrivalTime = extras.getLong("arrivalTime");
		long deptTime = extras.getLong("deptTime");
		
		
		// Create database entry
		ContentValues values = new ContentValues();
		values.put(AlarmDB.COLUMN_DEST, dest);
		values.put(AlarmDB.COLUMN_ARRIVALTIME, arrivalTime);
		values.put(AlarmDB.COLUMN_DEPTTIME, deptTime);
		
		database.insert(AlarmDB.TABLE_NAME, null, values);
		
		// Sets notification

		Cursor cursor = database.rawQuery("select _id from alarms where destination = ? and arrivalTime = ? and deptTime = ?", new String[] {dest, "" + arrivalTime, "" + deptTime});
		cursor.moveToFirst();
		Log.e("TAG", "ID: " + cursor.getLong(0));
		
		// Set and launch notification
		Intent intent = new Intent(this, CancelAlarm.class);
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
