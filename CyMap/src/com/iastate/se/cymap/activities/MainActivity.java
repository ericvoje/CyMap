package com.iastate.se.cymap.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.iastate.se.cymap.R;

/**
 * Note: Do not set the content view of each activity from this page. The
 * content view must be set at the activities themselves.
 * 
 * @author Colin Duffy
 * 
 */
public class MainActivity extends Activity {

	private boolean batterySaving;

	private Button btnMap;
	private Button btnViewSched;
	private Button btnAddApt;
	private Button btnBatteryMode;

	private Context thisCtx;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		thisCtx = this;
		setContentView(R.layout.activity_main);
		batterySaving = false;
		setActivityBackgroundColor(Color.RED);
		loadObjects();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	/**
	 * Loads all the buttons for the multiple views (Possibly deprecating this
	 * view, and initialize on Schedule view)
	 */
	public void loadObjects() {
		btnMap = (Button) findViewById(R.id.btnMap);
		btnViewSched = (Button) findViewById(R.id.btnViewSched);
		btnAddApt = (Button) findViewById(R.id.btnAddApt);
		btnBatteryMode = (Button) findViewById(R.id.btnBatteryMode);

		/**
		 * Sets the on click listener for the Map class
		 */
		btnMap.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				// Start MapActivity based on battery mode
				Intent i;
				if (batterySaving) {
					i = new Intent(thisCtx, AltMapActivity.class);
				} else {
					i = new Intent(thisCtx, MapActivity.class);
				}
				i.putExtra("BATTERY_MODE", batterySaving);
				startActivity(i);
			}
		});

		/**
		 * Sets the on click listener for the Schedule class
		 */
		btnViewSched.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {

				final String packageName = "com.iastate.se.cymap.schedule";

				try {
					Class<?> c = Class.forName(packageName + "."
							+ "SchedListActivity");
					Intent i = new Intent(thisCtx, c);
					i.putExtra("BATTERY_MODE", batterySaving);
					startActivity(i);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		/**
		 * Sets the on click listener for the Add Appointment class
		 */
		btnAddApt.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent i = new Intent(thisCtx, AddCourseActivity.class);
				i.putExtra("BATTERY_MODE", batterySaving);
				startActivity(i);
			}
		});

		/**
		 * Sets the on click listener for the Battery savings toggle
		 */
		btnBatteryMode.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				if (batterySaving) {
					// Turn off battery savings
					setActivityBackgroundColor(Color.RED);
					adjustBrightness(200);
					Toast toast = Toast.makeText(getApplicationContext(),
							"Battery Savings Off", Toast.LENGTH_SHORT);
					toast.show();
				} else {
					// Turn on battery savings
					setActivityBackgroundColor(Color.BLACK);
					adjustBrightness(30);
					Toast toast = Toast.makeText(getApplicationContext(),
							"Battery Savings On", Toast.LENGTH_SHORT);
					toast.show();
				}
				batterySaving = !batterySaving;
			}
		});

	}

	public void setActivityBackgroundColor(int color) {
		View view = this.getWindow().getDecorView();
		view.setBackgroundColor(color);
	}

	public void adjustBrightness(int val) {
		android.provider.Settings.System.putInt(this.getContentResolver(),
				android.provider.Settings.System.SCREEN_BRIGHTNESS, val);
	}

}
