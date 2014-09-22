package com.systaxlab.arbaeen;

import java.util.ArrayList;

import com.systaxlab.arbaeen.utils.HadeethDBAdapter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

public class NawawyMain extends Activity {

	private HadeethDBAdapter dbHelper;
	 private SimpleCursorAdapter dataAdapter;
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.nawawy_list);
	
	    final ListView listview = (ListView) findViewById(R.id.hList);
	    
	    dbHelper = new HadeethDBAdapter(this);
	    dbHelper.open();
	   
	    displayListView();
	    
	}


	private void displayListView() {
		// TODO Auto-generated method stub.
		Cursor cursor = dbHelper.fetchAllCountries();
		
		String [] columns = new String[] 
				{HadeethDBAdapter.KEY_ID,HadeethDBAdapter.KEY_HADEETH,HadeethDBAdapter.KEY_TITLE};
		// the XML defined views which the data will be bound to
		  int[] to = new int[] { 
		    R.id.hid,
		    R.id.hDetails,
		    R.id.hTitle,
		  };
		  
		  dataAdapter = new SimpleCursorAdapter(
				    this, R.layout.hadeethitem, 
				    cursor, 
				    columns, 
				    to,
				    0);
		  
		  
		  ListView listView = (ListView) findViewById(R.id.hList);
		  // Assign adapter to ListView
		  listView.setAdapter(dataAdapter);
		  
		  
	}
}


