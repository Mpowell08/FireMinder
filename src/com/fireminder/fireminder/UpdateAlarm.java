package com.fireminder.fireminder;


import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;

public class UpdateAlarm extends Activity {

	LocationManager lm;
	LocationListener ll;
	Location current_location;
	Integer time_to_dest, margin_of_error;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LocationInit();
		Bundle extras = getIntent().getExtras();
		String destination = extras.getString("destination");
		
		FireTime ft = new FireTime(current_location.getLongitude(), 
				current_location.getLatitude(), destination, ""); 
		GetTime gt = new GetTime(); 
		gt.execute(ft); //will call set_alarm() on complete
		lm.removeUpdates(ll);
	}
	private class GetTime extends AsyncTask<FireTime, Void, Integer>{

		@Override
		protected Integer doInBackground(FireTime... params) {
			FireTime ft = params[0];
			return ft.getTime();
		}
		protected void onPostExecute(Integer result){
			time_to_dest = result;
			margin_of_error = time_to_dest/4;
		}
	
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
