package com.fireminder.fireminder;

import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.view.Menu;

public class CancelAlarm extends Activity {

	static final int uniqueID = 12028;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(uniqueID);
		
		// delete database entry
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_cancel_alarm, menu);
		return true;
	}

}
