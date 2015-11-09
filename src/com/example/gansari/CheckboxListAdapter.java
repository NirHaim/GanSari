package com.example.gansari;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * BaseAdapter implementation which will inflate the layout "element_example".
 * 
 * You can find more information in my <a href="http://schimpf.es">blog</a>.
 * 
 * @see <a href="http://schimpf.es">Blog</a>
 * @author Gerrit Schimpf
 * 
 */
public class CheckboxListAdapter extends BaseAdapter implements OnClickListener {

	
	/** The inflator used to inflate the XML layout */
	private LayoutInflater inflator;
	private int N;
	private ChildrenDB children;
	
	/** A list containing some sample data to show. */
	private List<SampleData> dataList;

	public CheckboxListAdapter(LayoutInflater inflator) {
		super();
		this.inflator = inflator;

		dataList = new ArrayList<SampleData>();
		children.getClass();
		//children=new ChildrenDB();
		SQLiteDatabase db= children.getReadableDatabase();
		String sql = "SELECT * FROM CHILDREN";
		Cursor c = db.rawQuery(sql, null);
		N = c.getCount();
		
		for (int i=0; i<N;i++)
		{
			dataList.add(new SampleData("ילד"+String.valueOf(i), false, 1));
		}
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {

		// We only create the view if its needed
		if (view == null) {
			view = inflator.inflate(R.layout.list, null);

			// Set the click listener for the checkbox
			view.findViewById(R.id.checkBox1).setOnClickListener(this);
		}

		SampleData data = (SampleData) getItem(position);

		// Set the example text and the state of the checkbox
		CheckBox cb = (CheckBox) view.findViewById(R.id.checkBox1);
		cb.setChecked(data.isSelected());
		// We tag the data object to retrieve it on the click listener.
		cb.setTag(data);

		TextView tv = (TextView) view.findViewById(R.id.textView1);
		tv.setText(data.getName());
		
		Button message = (Button) view.findViewById(R.id.button1);
		Button call = (Button) view.findViewById(R.id.button2);
		
		return view;
	}

	@Override
	/** Will be called when a checkbox has been clicked. */
	public void onClick(View view) {
		SampleData data = (SampleData) view.getTag();
		data.setSelected(((CheckBox) view).isChecked());
	}

}