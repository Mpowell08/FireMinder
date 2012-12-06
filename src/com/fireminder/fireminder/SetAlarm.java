package com.fireminder.fireminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.holoeverywhere.app.DatePickerDialog;
import org.holoeverywhere.app.DatePickerDialog.OnDateSetListener;
import org.holoeverywhere.app.TimePickerDialog;
import org.holoeverywhere.app.TimePickerDialog.OnTimeSetListener;
import org.holoeverywhere.widget.DatePicker;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
//import android.app.TimePickerDialog;
//import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
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
	DistanceMatrix distance_matrix;

	Integer current_hour;
	Integer current_minute;
	Integer current_day;
	Integer current_month;
	Integer current_year;
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
	final OnDateSetListener odsl = new OnDateSetListener(){

		public void onDateSet(org.holoeverywhere.widget.DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			current_year = year; current_month = monthOfYear; current_day = dayOfMonth;
			TextView tv2 = (TextView) findViewById(R.id.date_chosen);
			monthOfYear++;
			tv2.setText(""+monthOfYear+"/"+dayOfMonth+"/"+year);
		}
		
	};
	final OnTimeSetListener otsl = new OnTimeSetListener(){



		public void onTimeSet(org.holoeverywhere.widget.TimePicker view,
				int hourOfDay, int minute) {
			current_hour = hourOfDay; current_minute = minute;
			String helper="";
			if(minute == 0) {helper = "" + 0;}
			TextView tv = (TextView) findViewById(R.id.time_chosen);
			if(hourOfDay > 12) {hourOfDay = hourOfDay-12;}
			tv.setText(""+hourOfDay+":"+minute+helper);
			
			
		}
		
	};
	public void timepickerbutton(View v){
		Calendar cal = Calendar.getInstance();
		TimePickerDialog timePickDiag = new TimePickerDialog(this, otsl, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false);
		timePickDiag.show();
	}
	public void datepickerbutton(View v){
		Calendar cal = Calendar.getInstance();
		DatePickerDialog datePickDiag = new DatePickerDialog(this, odsl, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		datePickDiag.show();
	}   
	 OnClickListener arrival_time_listener = new OnClickListener(){
		public void onClick(View v) {
			if(current_location != null){
				Calendar arrival_calendar = Calendar.getInstance();
			//	current_hour = arrival_time_tp.getCurrentHour();
			//	current_minute = arrival_time_tp.getCurrentMinute();
				
				arrival_calendar.set(Calendar.DAY_OF_MONTH, current_day);
				arrival_calendar.set(Calendar.MONTH, current_month);
				arrival_calendar.set(Calendar.YEAR, current_year);
				arrival_calendar.set(Calendar.HOUR_OF_DAY, current_hour);
				arrival_calendar.set(Calendar.MINUTE, current_minute);
				distance_matrix = new DistanceMatrix(destination.getEditableText().toString(), current_location, arrival_calendar.getTimeInMillis());
				GetDistanceMatrix gdm = new GetDistanceMatrix();
				gdm.execute(distance_matrix);
				
				lm.removeUpdates(ll);
				finish();
			} else {
				Toast.makeText(getApplicationContext(), "No location known at the moment", Toast.LENGTH_SHORT).show();
			}
		} 
	}; 

	
	public void set_alarm(){
		Log.e("debug", distance_matrix.toString());
		if(distance_matrix.status.contains("OK")){
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(distance_matrix.arrival_time - distance_matrix.duration);
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm aaa");
			
			//Output the guesstimated time
			Toast.makeText(getApplicationContext(), sdf.format(calendar.getTime()), Toast.LENGTH_LONG).show();	
			
			//Notifications
			String body = "Leave for " + distance_matrix.destination + "at" + sdf.format(calendar.getTime());
			String title = "" + sdf.format(calendar.getTime());

			Intent alarmIntent = new Intent(getApplicationContext(), LaunchNotification.class);
			alarmIntent.putExtra("destination", distance_matrix.destination);
			alarmIntent.putExtra("arrival_time", distance_matrix.arrival_time);
			alarmIntent.putExtra("dept_time", calendar.getTimeInMillis());
			alarmIntent.putExtra("title", title);
			alarmIntent.putExtra("body", body);
			
			AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
			PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, alarmIntent, 0);
			am.cancel(pi);
			am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
		
		} else {
			Toast.makeText(getApplicationContext(), "Invalid address", Toast.LENGTH_SHORT).show();
		}

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
	
	public void SetupInit(){
		findViewsById();
		done_button.setOnClickListener(arrival_time_listener);
		//Initial time and date variables
		Calendar current = Calendar.getInstance();
		current_hour = current.getTime(Calendar.HOUR_OF_DAY);
		current_minute = current.getTime(Calendar.MINUTE);
		current_day = current.getTime(Calendar.DAY_OF_MONTH);
		current_month = current.getTime(Calendar.MONTH);
		current_year = current.getTime(Calendar.YEAR);
	}
	public void findViewsById(){
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
	    //	lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, UPDATE_TIME_MS, 0, ll);
	    	current_location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	    }
}
