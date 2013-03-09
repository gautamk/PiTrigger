package com.gautamk.pitrigger;

import java.io.IOException;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {

	private ToggleButton turnOn;
	private String baseURL = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		turnOn = (ToggleButton) findViewById(R.id.piswitch);
		
		turnOn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					new HttpCalls(baseURL+"/on").execute();
				else
					new HttpCalls(baseURL+"/off").execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		LayoutInflater inflater =  getLayoutInflater();
		View view = inflater.inflate(R.layout.url_dialog, null,false);
		final EditText urlTextview = (EditText) view.findViewById(R.id.urlString);
		urlTextview.setText(baseURL);
		new AlertDialog.Builder(this)
		.setView(view)
		.setPositiveButton("Save", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				baseURL = urlTextview.getText().toString();
			}
		})
		.show()
		;
		return true;
	}

	
	private class HttpCalls extends AsyncTask<Void, Void, Void>
	{

		private String url;
		
		public HttpCalls(String url) {
			this.url = url;
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			
			/*
			final AndroidHttpClient httpClient = AndroidHttpClient.newInstance(this.getClass().getSimpleName());
			HttpUriRequest request = new Ht	tpGet(url);
			try {
				HttpResponse response = httpClient.execute(request);
			} catch (IOException e) {
				e.printStackTrace();
			}catch (IllegalStateException e) {
				Toast.makeText(getApplicationContext(), "The Url was invalid : "+ url , Toast.LENGTH_SHORT).show();
			}
			return null;*/
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        String responseBody = null;
	        HttpGet get = new HttpGet(url);
	        JSONObject json = null;

	        try {
	            responseBody = httpClient.execute(get, responseHandler);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			return null;
	        
		}
		
		@Override
		protected void onPostExecute(Void paramsVoid)
		{
			Toast.makeText(getApplicationContext(), "Message was sent to "+ url , Toast.LENGTH_SHORT).show();
		}
		
	}
}
