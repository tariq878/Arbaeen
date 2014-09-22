package com.systaxlab.arbaeen;

import java.io.IOException;

import com.systaxlab.arbaeen.utils.DatabaseHelper;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Nawawy2 extends ActionBarActivity {
	
	private WebView webview;
	private int selectedIndex = 0;
	private TextView txtTitle;
	private TextView txtCount;
	private Handler myHandler = new Handler();
	private Button btnForward;
	private Button btnBack;
	static Cursor cursor ;
	private DatabaseHelper helper;
	
	private Runnable mMyRunnable = new Runnable()
	 {
	     @Override
	     public void run()
	     {
	 			try {
	 				
	 				cursor = helper.getHadeethById(""+(selectedIndex));		
	 				if(cursor.moveToFirst()){
	 					do{
	 						webview.loadData(getHTML(cursor.getString(1)), "text/html; charset=UTF-8", null);
	 						txtTitle.setText(" * " + cursor.getString(2)+ " * ");
	 						txtCount.setText(selectedIndex +"/42");
	 					}while(cursor.moveToNext());
	 				}
	 				else{
	 					//t.setText(view.getResources().getText(R.string.intro));
	 				}
	 				
	 			} catch(Exception e)
	 			{
	 				e.printStackTrace();
	 			}
	     }

	  };
	  
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main2);
		
		//Create the database. 
		helper = new DatabaseHelper(this);
		
		txtTitle = (TextView)findViewById(R.id.textTitle2);
		txtCount = (TextView)findViewById(R.id.count);
		webview = (WebView)findViewById(R.id.webkit2);
		
		btnForward = (Button)findViewById(R.id.ic_action_forward);
		btnBack = (Button)findViewById(R.id.ic_action_back);
		//test.
		
		//Create the database 
		  try {
	            helper.createDataBase();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	         Toast.makeText(this, "Err"+e.getMessage(), Toast.LENGTH_SHORT).show();
	        }
		
	}
	
	 @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
 
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	     // Handle presses on the action bar items
	     switch (item.getItemId()) {
	         case R.id.ic_action_back:
	        	 selectedIndex = (selectedIndex < 42 ?selectedIndex + 1 :selectedIndex);
	        	 item.setEnabled(selectedIndex==42?false:true);
	             loadHadith(selectedIndex);
	             return true;
	         case R.id.ic_action_forward:
	        	 //The right arrow.
	        	 selectedIndex = (selectedIndex > 1 ?selectedIndex -1 :selectedIndex);
	        	 item.setEnabled(selectedIndex==0?false:true);
	        	 loadHadith(selectedIndex);
	             return true;
	         default:
	             return super.onOptionsItemSelected(item);
	     }
	 }

	 

	private String getHTML(String string) {
		String header = "<html dir=\"rtl\"> <HEAD><title></title><style>.GodDesc { 	font-size: 22px; 	" +
				"font-weight: normal; 	text-align: right; } @font-face {     font-family: arabic;     " +
				"src: url(\"file:///android_asset/fonts/arabic2.ttf\") } body    {	font-family: arabic; 	" +
				"margin:0 }</style></HEAD><body BGCOLOR=\"#E0E8F0\" dir=\"rtl\" marginwidth=\"0\" marginheight=\"0\">" +
				" <table><tr><td class=\"GodDesc\"> ";
		
		String footer = "</td></tr></table></body></html>";
		String body = string;
		
		return header+body+footer;
	}
	
	
	
	private void loadHadith(int i) {
		txtTitle.setText("تحميل ...");
		
		myHandler.removeCallbacks(mMyRunnable);
		myHandler.postDelayed(mMyRunnable, 500);

	}
}
