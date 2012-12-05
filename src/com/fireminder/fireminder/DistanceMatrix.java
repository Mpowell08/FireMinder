package com.fireminder.fireminder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.util.Log;

public class DistanceMatrix {
	
	public String destination;
	private Location current_location;
	public long arrival_time;
	public int distance;
	public long duration;
	public String origin;
	public String status;
	
	public DistanceMatrix(String destination, Location current_location, long arrival_time){
		this.destination = destination;
		this.current_location = current_location;
		this.arrival_time = arrival_time;
		
	}
	@Override
	public String toString(){
		return "" + current_location.toString() + " " + destination + " " + duration + " " + distance + " " + status;
	}
	public String QueryURL(){
		String URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
				current_location.getLatitude() + "," + current_location.getLongitude() +
				"&destinations=" + destination.replace(" ", "+") +
				"&mode=driving&language=en-US&sensor=false";
		return URL;
	}
	
	public void getDistanceMatrix(){
		DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpPost httppost = new HttpPost("" + QueryURL());
		httppost.setHeader("Content-type", "application/json");
		
		InputStream inputStream = null;
		String result = null;
		try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			
			inputStream = entity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null)
			{
			    sb.append(line + "\n");
			}
			result = sb.toString();
			try {
				JSONObject jObject = new JSONObject(result);
				JSONObject rootObject = new JSONObject(result); // Parse the JSON to a JSONObject
				this.status = rootObject.getString("status");
				this.destination = rootObject.getString("destination_addresses");
				this.origin = rootObject.getString("origin_addresses");
				
			
	            JSONArray rows = rootObject.getJSONArray("rows"); // Get all JSONArray rows

	            for(int i=0; i < rows.length(); i++) { // Loop over each each row
	                JSONObject row = rows.getJSONObject(i); // Get row object
	                JSONArray elements = row.getJSONArray("elements"); // Get all elements for each row as an array

	                for(int j=0; j < elements.length(); j++) { // Iterate each element in the elements array
	                    JSONObject element =  elements.getJSONObject(j); // Get the element object
	                    JSONObject duration = element.getJSONObject("duration"); // Get duration sub object
	                    JSONObject distance = element.getJSONObject("distance");

	                    this.duration = duration.getInt("value")*1000; // Print int value
	                    this.distance = distance.getInt("value");
	                }
	            }
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
