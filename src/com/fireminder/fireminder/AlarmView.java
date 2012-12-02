package com.fireminder.fireminder;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class AlarmView extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_view);
        
    }
    
    public void launch_set_alarm(View v){
    	Intent i = new Intent();
    	i.setClass(getApplicationContext(), SetAlarm.class);
    	startActivity(i);
    }


    
}
