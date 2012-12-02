package com.fireminder.fireminder;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	EditText address_line_1, address_line_2;
	Button submit_button;
	TextView stdout_textview;
	LocationManager lm;
	LocationListener ll;
	Location current_location;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        SetupInit();
        
		submit_button.setOnClickListener(submit_button_listener);
        
    }
    
    public void onPause(){
    	super.onPause();
    	lm.removeUpdates(ll);
    }
    
    public void onResume(){
    	super.onResume();
    	LocationInit();
    }
    
    public OnClickListener submit_button_listener = new OnClickListener(){
		public void onClick(View v) {

			new Thread(new Runnable(){
				String stdout;
				public void run() {
					if(current_location != null){
						FireTime ft = new FireTime(current_location.getLongitude(), current_location.getLatitude(), address_line_1.getEditableText().toString(), address_line_2.getEditableText().toString());
						stdout = "" + ft.getTime();
						
					} else {
						stdout = "No location currently";
					}
					stdout_textview.post(new Runnable() {
						public void run(){
							stdout_textview.setText(stdout);
						}
					});
				}
				
				
			}).start();
	/*		if(current_location != null){
				FireTime ft = new FireTime(current_location.getLongitude(), current_location.getLatitude(), address_line_1.getEditableText().toString(), address_line_2.getEditableText().toString());
				stdout = "" + ft.getTime();
				
			} else {
				stdout = "No location currently";
			} 
			
			stdout_textview.setText(stdout);
			*/
		}
    };
    
    public void findViewsById(){
    	address_line_1 = (EditText) findViewById(R.id.address_line_1);
    	address_line_2 = (EditText) findViewById(R.id.address_line_2);
    	submit_button = (Button) findViewById(R.id.submit_button);
    	stdout_textview = (TextView) findViewById(R.id.stdout_textview);
    	
    }
   
    public void SetupInit(){
    	findViewsById();
    	LocationInit();
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
    
    public void launch_alarm_view(View v){
    	Intent i = new Intent();
    	i.setClass(getApplicationContext(), AlarmView.class);
    	startActivity(i);
    }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}
