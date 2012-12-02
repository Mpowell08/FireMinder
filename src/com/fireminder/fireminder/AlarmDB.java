package com.fireminder.fireminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDB extends SQLiteOpenHelper{
	
	public static final String TABLE_NAME = "alarms";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_DEST = "destination";
	public static final String COLUMN_ARRIVALTIME = "arrivalTime";
	public static final String COLUMN_DEPTTIME = "deptTime";
	
	public static final String CREATE_TABLE = "create table alarms " +
												"( _id integer primary key autoincrement, " +
												"destination text not null, " +
												"arrivalTime int not null," +
												"deptTime int not null" +
												");";
	
	public static final String DATABASE_NAME = "alarms.db";
	public static final int DATABASE_VERSION = 1;
	
	public AlarmDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
		onCreate(db);
		
	}
	
	

}
