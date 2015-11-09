package com.example.gansari;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		View addButton = findViewById(R.id.button1);
		addButton.setOnClickListener(this);
		View deleteButton = findViewById(R.id.button2);
		deleteButton.setOnClickListener(this);
		View presenceButton = findViewById(R.id.button3);
		presenceButton.setOnClickListener(this);
		View dohButton = findViewById(R.id.button4);
		dohButton.setOnClickListener(this);
		View exitButton = findViewById(R.id.button5);
		exitButton.setOnClickListener(this);	
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.button1:
			Intent i = new Intent(this, AddChild.class);
			startActivity(i);
			break;
		case R.id.button2:
			Intent j = new Intent(this, DeleteChild.class);
			startActivity(j);
			break;
		case R.id.button3:
			Intent k = new Intent(this, Presence.class);
			startActivity(k);
			break;
		}
	}
}