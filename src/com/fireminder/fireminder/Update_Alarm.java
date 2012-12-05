package com.fireminder.fireminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fireminder.fireminder.SetAlarm.GetDistanceMatrix;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class Update_Alarm extends Activity {
	LocationManager lm;
	LocationListener ll;
	DistanceMatrix distance_matrix;
	Location current_location;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle extras = getIntent().getExtras();
		String dest = extras.getString("destination");
		long arrivalTime = extras.getLong("arrival_time");
		long dept_time = extras.getLong("dept_time");
		String title = extras.getString("title");
		String body = extras.getString("body");
	/*	
		Intent alarmIntent = new Intent(getApplicationContext(), LaunchNotification.class);
		alarmIntent.putExtra("destination", distance_matrix.destination);
		alarmIntent.putExtra("arrival_time", distance_matrix.arrival_time);
		alarmIntent.putExtra("dept_time", dept_time);
		alarmIntent.putExtra("title", title);
		alarmIntent.putExtra("body", body);
		
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, alarmIntent, 0);
		am.cancel(pi);
		am.set(AlarmManager.RTC_WAKEUP, dept_time, pi); */
		lm.removeUpdates(ll);
		finish();
	}
	public void set_alarm(){
		
	}
	public class GetDistanceMatrix extends AsyncTask<DistanceMatrix, Void, DistanceMatrix>{ 

		@Override
		protected DistanceMatrix doInBackground(DistanceMatrix... params) {
			DistanceMatrix dm = params[0];
			dm.getDistanceMatrix();
			return dm;
		}
		
		protected void onPostExecute(DistanceMatrix result){
			distance_matrix = result;
			set_alarm();
		}
		
	} 

	public void LocationInit(){
	}
}
