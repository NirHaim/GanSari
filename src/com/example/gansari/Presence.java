package com.example.gansari;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Presence extends ListActivity implements OnClickListener
{
	//define  the columns of the table in the DB
	private static String[] FROM = { "_NAME", "COMING",};
	private SharedPreferences prefs;
	private SharedPreferences prefs1;
	private Editor editor;

	private int year, month, day, N;
	private CheckBox cb; 
	private CheckBox cbb;
	private String names[];
	private int coming[];
	private TextView tvDate, tvPresence, tvAbsent;
	private CheckedTextView ctv[];
	private static String date, currentDate;
	private ChildrenDB children;
	private SQLiteDatabase db;
	public static int presence=0;
	public static int Id;
	public String k1, k2;
	public final int SMS_DIALOG_ID= 1;
	public final int CALL_DIALOG_ID= 2;

	class CheckboxListAdapter extends BaseAdapter implements OnClickListener
	{
		/** The inflator used to inflate the XML layout */
		private LayoutInflater inflator;
		private int N;

		/** A list containing some sample data to show. */
		private List<SampleData> dataList;

		public CheckboxListAdapter(LayoutInflater inflator) {
			super();
			this.inflator = inflator;

			dataList = new ArrayList<SampleData>();

			children= new ChildrenDB(getBaseContext());
			db= children.getReadableDatabase();
			String sql = "SELECT * FROM CHILDREN";
			Cursor c = db.rawQuery(sql, null);
			N = c.getCount();

			Cursor q= db.rawQuery("SELECT * FROM CHILDREN", null);
			while(q.moveToNext())
			{
				String fName= q.getString(1);
				String lName= q.getString(2);
				dataList.add(new SampleData(fName+" "+lName, false, 1));
			}
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return dataList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View view, ViewGroup viewGroup) 
		{
			// We only create the view if its needed
			if (view == null) 
			{
				view = inflator.inflate(R.layout.list, null);

				// Set the click listener for the checkbox
				view.findViewById(R.id.checkBox1).setOnClickListener(this);
				tvPresence=(TextView) findViewById(R.id.textView5);
				tvAbsent=(TextView) findViewById(R.id.textView6);
				tvPresence.setText(String.valueOf(0));
				tvAbsent.setText(String.valueOf(N));
			}

			final SampleData data = (SampleData) getItem(position);

			
			// Set the example text and the state of the checkbox
			cb = (CheckBox) view.findViewById(R.id.checkBox1);
				
			cb.setOnCheckedChangeListener(null);
			cb.setChecked(data.isSelected());
			
			cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView ,boolean isChecked)
				{
					if (isChecked==true)
					{
						presence++;
					}
					else
					{
						presence--;
					}
					tvPresence.setText(String.valueOf(presence));
					tvAbsent.setText(String.valueOf(N-presence));
					
				}
			});
//			cb.setChecked(data.isSelected());
		
			Button message = (Button) view.findViewById(R.id.button1);
			if (message!= null)
			{
				message.setTag(position);
				message.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						Id= position+1;
						showDialog(SMS_DIALOG_ID);
						Toast.makeText(getBaseContext(),"the Id is: "+ Id +"  the position is: "+position, 
								Toast.LENGTH_LONG).show();
					}
				}
						);
			}

			Button call = (Button) view.findViewById(R.id.button2);			
			if (call != null)
			{
				call.setTag(position);
				call.setOnClickListener(new OnClickListener() 
				{
					@Override
					public void onClick(View v) 
					{
						Id= position+1;
						showDialog(CALL_DIALOG_ID);
						Toast.makeText(getBaseContext(),"the Id is: "+ Id +"  the position is: "+position, 
								Toast.LENGTH_LONG).show();
					}
				}
						);
			}

			//			if (data.getVisibility()==0)
			//				cb.setVisibility(View.GONE);
			//
			//			else
			//				cb.setVisibility(View.VISIBLE);

			// We tag the data object to retrieve it on the click listener.
			cb.setTag(data);////////////////////

			
			TextView tv = (TextView) view.findViewById(R.id.textView1);
			tv.setTextSize(20);
			tv.setText(data.getName());

			return view;
		}

		@Override
		/** Will be called when a checkbox has been clicked. */
		public void onClick(View view) {
			SampleData data = (SampleData) view.getTag();
			data.setSelected(((CheckBox) view).isChecked());
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) 
	{
		
		Cursor q= db.rawQuery("SELECT KESHER1, KESHER2 FROM CHILDREN WHERE _ID = '"+Id+"'", null);
		while (q.moveToNext())
		{
			k1= q.getString(0);
			k2= q.getString(1);
		}
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if (id==1)
		{
			builder.setTitle("בחר/י איש קשר לשליחת הודעה");
		}
		else if (id==2)
		{
			builder.setTitle("בחר/י איש קשר לחיוג");
		}

		ListView modeList = new ListView(this);
		String[] stringArray = new String[] { k1, k2 };
		ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stringArray);
		modeList.setAdapter(modeAdapter);

		builder.setView(modeList);
		final Dialog dialog = builder.create();

		dialog.show();

		return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.presence);

		children=new ChildrenDB(this);
		SQLiteDatabase db= children.getReadableDatabase();
		String sql = "SELECT * FROM CHILDREN";
		Cursor c = db.rawQuery(sql, null);
		N = c.getCount();
		Toast.makeText(getBaseContext(), "כמות הילדים היא: "+ N, 
				Toast.LENGTH_LONG).show();

		CheckboxListAdapter adapter = new CheckboxListAdapter(
				getLayoutInflater());

		getListView().setAdapter(adapter);
		


//		cb= new CheckBox[N+1];
		names= new String[N+1];
		coming= new int[N+1];
		ctv= new CheckedTextView[N+1];

		//		Button b = new Button(this);
		//		b.setText("אישור");
		//		LinearLayout ll = new LinearLayout(this);
		//		ll.addView(b);


		//		View okButton = findViewById(R.id.button1);
		//		okButton.setOnClickListener(this);

//		tvPresence=(TextView) findViewById(R.id.textView5);
//		tvAbsent=(TextView) findViewById(R.id.textView6);
		tvDate=(TextView) findViewById(R.id.textView2);


		
		
		
		prefs = getSharedPreferences("myPref", MODE_WORLD_READABLE);
		boolean isStarted = prefs.getBoolean("start", false);

//		tvPresence.setText(String.valueOf(presence));
//		tvAbsent.setText(String.valueOf(N-presence));


		//		//Define all the checkBoxes
		//		for(int i=1; i<=N; i++)
		//		{
		//			String checkBoxID = "checkBox" + i;
		//			String checkedTextViewID= "checkedTextView" + i;
		//			int resID = getResources().getIdentifier(checkBoxID, "id", "com.example.gansari");
		//			int textID = getResources().getIdentifier(checkedTextViewID, "id", "com.example.gansari");
		//			cb[i] = (CheckBox)findViewById(resID);
		//			ctv[i] = ((CheckedTextView) findViewById(textID));
		//			names[i]=ctv[i].getText().toString();
		//
//					cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//		
//						@Override
//						public void onCheckedChanged(CompoundButton buttonView ,boolean isChecked)
//						{
//							if (isChecked==true)
//							{
//								presence++;
//							}
//							else
//							{
//								presence--;
//							}
//							tvPresence.setText(String.valueOf(presence));
//							tvAbsent.setText(String.valueOf(N-presence));
//						}
//					});
		//
		//			if(!isStarted)
		//			{
		//				addChild(names[i], 0);
		//				if (i==N)
		//				{
		isStarted= true;
		prefs = getSharedPreferences("myPref", MODE_WORLD_WRITEABLE);
		editor = prefs.edit();
		editor.putBoolean("start", isStarted);
		setCurrentDate();
		editor.putString("date", currentDate);
		editor.commit();
		//				}
		//			}
		//			else
		//			{
		//				//Access to the DB for acquire setting to show on activity
		//				SQLiteDatabase db= children.getReadableDatabase();
		//				Cursor q= db.rawQuery("SELECT * FROM PRESENCE WHERE _NAME='"+names[i]+"'", null);
		//
		//				while(q.moveToNext())
		//				{
		//					int val= q.getInt(1);
		//
		//					if(val==1)
		//					{
		//						cb[i].setChecked(true);
		//					}
		//				}
		//			}
		//		}
		//		prefs = getSharedPreferences("myPref", MODE_WORLD_READABLE);
		//		setCurrentDate();
		//		date = prefs.getString("date", null);
		//		if(!date.equals(currentDate))
		//		{
		//			for(int i=1; i<=N; i++)
		//			{
		//				cb[i].setChecked(false);
		//				addChild(names[i], 0);
		//			}
		//			prefs = getSharedPreferences("myPref", MODE_WORLD_WRITEABLE);
		//			editor = prefs.edit();
		//			editor.putString("date", currentDate);
		//			editor.commit();
		//			Toast.makeText(getBaseContext(), "בוקר טוב, שרי!", 
		//					Toast.LENGTH_LONG).show();
		//		}
	}

	//This method adds a row which represent a mode and its settings in DB table
	private void addChild(String name, int coming)
	{
		SQLiteDatabase db= children.getWritableDatabase();
		String sql = "SELECT * FROM PRESENCE WHERE _NAME = '" + name + "'";
		Cursor c = db.rawQuery(sql, null);
		int results = c.getCount();
		if(results == 0)
		{
			//If the mode doesn't exists therefore insert record
			SQLiteDatabase dbw= children.getWritableDatabase();
			ContentValues values= new ContentValues();
			values.put("_NAME", name);
			values.put("COMING", coming);
			dbw.insertOrThrow("PRESENCE", null, values); 
		}    
	}


	public void setCurrentDate()
	{
		final Calendar calendar = Calendar.getInstance();

		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);

		// set current date into edit text
		tvDate.setText(new StringBuilder()
		// Month is 0 based, so you have to add 1
		.append(day).append("-")
		.append(month + 1).append("-")
		.append(year).append(" "));
		currentDate=tvDate.getText().toString();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) 
	{
		SQLiteDatabase db= children.getWritableDatabase();

		ContentValues data= new ContentValues();
		for(int i=1; i<=N; i++)
		{
			coming[i]= (cb.isChecked()) ? 1 : 0;

			boolean test= cb.isChecked();

			data.put("COMING", coming[i]);
			db.update("PRESENCE", data, "_NAME='"+names[i]+"'", null);
		}

		//Back to Home Screen
		Intent setIntent = new Intent(Intent.ACTION_MAIN);
		setIntent.addCategory(Intent.CATEGORY_HOME);
		setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(setIntent);
		finish();
	}
}
