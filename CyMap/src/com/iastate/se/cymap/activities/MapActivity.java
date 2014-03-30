package com.iastate.se.cymap.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iastate.se.cymap.R;

/**
 * This class is used for controlling the map view
 * 
 * @author Colin Duffy
 * 
 */
public class MapActivity extends Activity {

	LatLng atanasoff = new LatLng(42.028170, -93.649709);
	LatLng carver = new LatLng(42.025260, -93.648333);
	LatLng campanile = new LatLng(42.0255, -93.6460);
	LatLng coover = new LatLng(42.02844, -93.65093);
	LatLng hoover = new LatLng(42.026651, -93.651164);

	GoogleMap map;
	Button viewButton;
	Spinner buildingSpinner;
	int mapView = GoogleMap.MAP_TYPE_NORMAL;

	/*
	 * LatLng hoover = new LatLng(); LatLng howe = new LatLng(); LatLng sweeny =
	 * new LatLng(); LatLng atanasoff = new LatLng(); LatLng library = new
	 * LatLng();
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(campanile, 17));

		map.addMarker(new MarkerOptions().title("Iowa State Campanile")
				.snippet("Central campus landmark").position(campanile));

		viewButton = (Button) findViewById(R.id.view_toggle_button);

		viewButton.setOnClickListener(new OnClickListener() {

			public void onClick(View view) {
				// Other supported types include: MAP_TYPE_NORMAL,
				// MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
				if (mapView == GoogleMap.MAP_TYPE_NORMAL) {
					map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
					mapView = GoogleMap.MAP_TYPE_HYBRID;
				} else {
					map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
					mapView = GoogleMap.MAP_TYPE_NORMAL;
				}
			}

		});

		buildingSpinner = (Spinner) findViewById(R.id.building_spinner);
		// Create an ArrayAdapter using the string array and a default spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.buildings_array,
				android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		buildingSpinner.setAdapter(adapter);

		buildingSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				LatLng moveTo = null;
				switch (pos) {
				case 0:
					moveTo = atanasoff;
					break;
				case 1:
					moveTo = carver;
					break;
				case 2:
					moveTo = campanile;
					break;
				case 3:
					moveTo = coover;
					break;
				case 7:
					moveTo = hoover;
					break;
				default:
					break;
				}

				if (moveTo != null) {
					map.moveCamera(CameraUpdateFactory
							.newLatLngZoom(moveTo, 17));
				}

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
	}
}
