package com.example.gansari;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

//This class create the DB table
public class ChildrenDB extends SQLiteOpenHelper
{
	public static int count=1;
	private final Context myContext;
	private static final String TAG = "MyActivity";
	private static final int DATABASE_VERSION = 2;
    
	
    public ChildrenDB(Context context)
	{
		super(context, "ChildrenDB", null, DATABASE_VERSION);
		this.myContext = context;
	}

    
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE " + "PRESENCE" + "("
				+ "_DATE" + " TEXT, "
				+ "CHILD" + " INTEGER,"
				+ "COMING" + " INTEGER);" );
		
		db.execSQL("CREATE TABLE " + "CHILDREN" + "(" + "_ID" + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "FIRSTNAME" + " TEXT, " + "LASTNAME" + " TEXT, "
				+ "KESHER1" + " TEXT, " + "TKESHER1" + " TEXT, "
				+ "KESHER2" + " TEXT, " + "TKESHER2" + " TEXT);" );
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
//		count++;
//		String upgradeQuery = "ALTER TABLE PRESENCE ADD COLUMN Y"+count+ " INTEGER";
//		
////		Toast.makeText(myContext, "oldVersion= "+oldVersion, 
////				Toast.LENGTH_SHORT).show();
//		Log.d(TAG, "onupgrade from ver "+oldVersion+"to ver "+ newVersion);
//		//if (oldVersion == 1 && newVersion == 2)
//		if(newVersion-oldVersion==1)
//		{
//			db.setVersion(newVersion);
//			
//			//db.execSQL(upgradeQuery);	
//		}
	}	
}