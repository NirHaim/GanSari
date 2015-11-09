package com.example.gansari;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddChild extends Activity implements OnClickListener
{
	private static String[] FROM = { "_ID", "FIRSTNAME", "LASTNAME", "KESHER1","TKESHER1", "KESHER2", "TKESHER2",};
	private static String ORDER_BY = "_ID" + " DESC";
	private ChildrenDB children;
	private EditText txtFName, txtLName, txtKesher1, txtTKesher1, txtTKesher2, txtKesher2;
	private SharedPreferences prefs;
	private Editor editor;
	public static int id=0;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);

		children=new ChildrenDB(this);

		txtFName= (EditText) findViewById(R.id.editText1);
		txtLName= (EditText) findViewById(R.id.editText2);
		txtKesher1= (EditText) findViewById(R.id.editText3);
		txtTKesher1= (EditText) findViewById(R.id.editText4);
		txtKesher2= (EditText) findViewById(R.id.editText5);
		txtTKesher2= (EditText) findViewById(R.id.editText6);

		View saveButton = findViewById(R.id.button1);
		saveButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		String fName= txtFName.getText().toString();
		String lName= txtLName.getText().toString();
		String kesher1= txtKesher1.getText().toString();
		String tKesher1= txtTKesher1.getText().toString();
		String kesher2= txtKesher2.getText().toString();
		String tKesher2= txtTKesher2.getText().toString();
		
		id++;
		addChild(fName, lName, kesher1, tKesher1, kesher2, tKesher2, id);

		Cursor cursor = getData(); 
		showData(cursor);

		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);	
	}

	private void addChild(String fName, String lName, String kesher1, 
			String tKesher1, String kesher2, String tKesher2, int ID)
	{
		SQLiteDatabase db= children.getWritableDatabase();
		String sql = "SELECT * FROM CHILDREN WHERE FIRSTNAME = '" + fName + "'" + "AND LASTNAME = '" + lName + "'";
		Cursor c = db.rawQuery(sql, null);
		int results = c.getCount();
		ContentValues values= new ContentValues();
		if(results == 0)
		{
			//If the child doesn't exists therefore insert record
			
			
			values.put("FIRSTNAME", fName);
			values.put("LASTNAME", lName);
			values.put("KESHER1", kesher1);
			values.put("TKESHER1", tKesher1);
			values.put("KESHER2", kesher2);
			values.put("TKESHER2", tKesher2);
			db.insertOrThrow("CHILDREN", null, values); 
			//			int oldVersion= dbw.getVersion();
			//			Toast.makeText(getBaseContext(), "The oldVersion is: "+ oldVersion, 
			//					Toast.LENGTH_SHORT).show();
			//			 db.beginTransaction();
			//						dbw.setVersion(oldVersion+1);
			//						db.setTransactionSuccessful();
			//						 db.endTransaction();
			//			children.onUpgrade(dbw, oldVersion, oldVersion+1);


			//			numOfChildren++;
			//			
			//			prefs = getSharedPreferences("myPref1", MODE_WORLD_WRITEABLE);
			//			editor = prefs.edit();
			//
			//			editor.putInt("nChildren",numOfChildren);		       
			//			editor.commit();

			//dbw.update("CHILDREN", values, "_FIRSTNAME='"+ fName+"'"+" AND LASTNAME='"+ lName+ "'", null);

			
		}
		else
		{
			Toast.makeText(getBaseContext(), "הילד כבר קיים ברשימה", 
					Toast.LENGTH_SHORT).show();
		}
		sql = "SELECT * FROM PRESENCE WHERE CHILD = '" + ID + "'";
		SQLiteDatabase dbw= children.getWritableDatabase();
		c = dbw.rawQuery(sql, null);
		results = c.getCount();
		ContentValues values2= new ContentValues();
		if(results == 0)
		{
			//If the child doesn't exists therefore insert record
			//				dbw= children.getWritableDatabase();	
			values2.put("_DATE", "26-8-2013");
			values2.put("CHILD", ID);
			values2.put("COMING", 0);
			dbw.insertOrThrow("PRESENCE", null, values2);

			Toast.makeText(getBaseContext(), fName+" "+lName+ " התווסף" , 
					Toast.LENGTH_SHORT).show();
		}
	}

	private Cursor getData() {
		// Perform a managed query. The Activity will handle closing
		// and re-querying the cursor when needed.
		SQLiteDatabase db = children.getReadableDatabase();
		Cursor cursor = db.query("CHILDREN", FROM, null, null, null, null, null);
		startManagingCursor(cursor);
		return cursor;
	}

	private void showData(Cursor cursor)
	{
		// Stuff them all into a big string
		StringBuilder builder = new StringBuilder( 
				"Saved Data:\n");
		while (cursor.moveToNext()) { 
			// Could use getColumnIndexOrThrow() to get indexes
			int Id1= cursor.getInt(0);
			String sId= String.valueOf(Id1);
			String fName = cursor.getString(1); 
			String lName = cursor.getString(2);
			String kesher1 = cursor.getString(3);
			String tKesher1 = cursor.getString(4);
			String kesher2 = cursor.getString(5);
			String tKesher2 = cursor.getString(6);

			builder.append(sId).append(": ");
			builder.append(fName).append(": "); 
			builder.append(lName).append(": ");
			builder.append(kesher1).append(": ");
			builder.append(tKesher1).append(": ");
			builder.append(kesher2).append(": ");
			builder.append(tKesher2).append("\n");

		}
		// Display on the screen
		setContentView(R.layout.datatable);
		TextView text = (TextView) findViewById(R.id.textView1); 
		text.setText(builder);
	}
}
