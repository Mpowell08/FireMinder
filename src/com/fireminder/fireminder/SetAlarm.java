package com.fireminder.fireminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetAlarm extends Activity{
	Button done_button;
	TimePicker arrival_time_tp;
	String arrival_time;
	EditText destination;
	LocationManager lm;
	LocationListener ll;
	Location current_location;
	TextView tv1;
	Integer time_to_dest;
	Integer margin_of_error;
	
	static final Integer notificationID = 139381;
	@Override
	public void onCreate(Bundle b){
		super.onCreate(b);
		setContentView(R.layout.set_alarm);
		LocationInit();
		SetupInit();
		
		
	}
	@Override
	public void onPause(){
		super.onPause();
		lm.removeUpdates(ll);
	}

	@Override
	public void onResume(){
		super.onResume();
		LocationInit();
	}
	

	OnClickListener arrival_time_listener = new OnClickListener(){
		public void onClick(View v) {
			if(current_location != null){
				FireTime ft = new FireTime(current_location.getLongitude(), 
						current_location.getLatitude(), destination.getEditableText().toString(), ""); 
				GetTime gt = new GetTime(); 
				gt.execute(ft); //will call set_alarm() on complete
				lm.removeUpdates(ll);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "No location known at the moment", Toast.LENGTH_SHORT).show();
			}
		}
	};

	public Calendar get_time(){

		// Get current time hour:minute
		Calendar calendar = Calendar.getInstance();
		
		//Set to our desired arrival time
		calendar.set(Calendar.HOUR_OF_DAY, arrival_time_tp.getCurrentHour());
		calendar.set(Calendar.MINUTE, arrival_time_tp.getCurrentMinute());
		
		//Subtract the travel time
		calendar.add(Calendar.MINUTE, -1 * time_to_dest);
		calendar.add(Calendar.MINUTE, -1 * margin_of_error);
		calendar.set(Calendar.DATE, Calendar.getInstance().getTime().getDate());

		return calendar;
		
	}
	public void set_alarm(){
		if(time_to_dest != null){
			
			Calendar calendar = get_time();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm aaa");
			
			//Output the guesstimated time
			Toast.makeText(getApplicationContext(), sdf.format(calendar.getTime()), Toast.LENGTH_LONG).show();	
			
			//Notifications
			NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			String body = "Leave for " + destination.getEditableText().toString() + " at " + sdf.format(calendar.getTime());
			String title = "" + sdf.format(calendar.getTime());

			Intent alarmIntent = new Intent(getApplicationContext(), LaunchNotification.class);
		   alarmIntent.putExtra("title", title);
		   alarmIntent.putExtra("body", body);
		   alarmIntent.putExtra("destination", destination.getEditableText().toString());
		   alarmIntent.putExtra("arrivalTime", Calendar.getInstance().getTimeInMillis());
		   alarmIntent.putExtra("deptTime", calendar.getTimeInMillis());
			AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
			PendingIntent PI = PendingIntent.getActivity(getApplicationContext(), 0, alarmIntent, 0);
			am.cancel(PI);
			am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), PI);
		
		} else {
			Toast.makeText(getApplicationContext(), "Invalid address", Toast.LENGTH_SHORT).show();
		}

	}
	
	public class GetTime extends AsyncTask<FireTime, Void, Integer>{

		@Override
		protected Integer doInBackground(FireTime... params) {
			FireTime ft = params[0];
			return ft.getTime();
		}
		protected void onPostExecute(Integer result){
			time_to_dest = result;
			margin_of_error = time_to_dest/4;
			set_alarm();
		}
	
}
	
	public void SetupInit(){
		findViewsById();
		done_button.setOnClickListener(arrival_time_listener);
	}
	public void findViewsById(){
		arrival_time_tp = (TimePicker) findViewById(R.id.arrival_time_tp);
		arrival_time_tp.setCurrentHour(12);
		arrival_time_tp.setCurrentMinute(30);
		done_button = (Button) findViewById(R.id.done_button);
		destination = (EditText) findViewById(R.id.destination);
		destination.setOnEditorActionListener(new OnEditorActionListener(){
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				destination.clearFocus();
				done_button.performClick();
				return false;
			}
		});
	}	
	 public void LocationInit(){
	    	long UPDATE_TIME_MS = 60000;
	    	lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
	    	ll = (LocationListener) new LocationListener() {

				public void onLocationChanged(Location arg0) {
					current_location = arg0;
				}

				public void onProviderDisabled(String arg0) {
					// TODO Auto-generated method stub
					
				}

				public void onProviderEnabled(String arg0) {
					// TODO Auto-generated method stub
					
				}

				public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
					// TODO Auto-generated method stub
					
				}
	    		
	    	};
	    	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, UPDATE_TIME_MS, 0, ll);
	    	lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, UPDATE_TIME_MS, 0, ll);
	    	current_location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	    }
}
