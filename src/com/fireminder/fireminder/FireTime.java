package com.fireminder.fireminder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

public class FireTime {
	protected String coordinates;
	protected String address_line;
	public int item1;
	public double time;
	
	/**
	 * Constructor
	 * Will combine latitude and longitude into the coordinates variable
	 * and address lines into a single line
	 * @param longitude
	 * @param latitude
	 * @param addr_1
	 * @param addr_2
	 */
	public FireTime(double longitude, double latitude, String addr_1, String addr_2){
		coordinates = ""+latitude+","+longitude;
		address_line = form_address(addr_1, addr_2);
	}
	private String getAddress_Line(){
		return address_line;
	}
	/**
	 * Changes whitespace to "+" and any commas to antistring.
	 * Commas are a special char in google maps api
	 * @param line1
	 * @param line2
	 * @return String
	 */
	private String form_address(String line1, String line2){
		line1 = line1.replace(" ", "+");
		line2 = line2.replace(" ", "+");
		line2 = line2.replace(",", "");
		return line1+"+"+line2;
	}
	/**
	 * Cocatenates origin and destination into single query to be used with api 
	 * @return String 
	 */
	public String create_query_url(){
		String url;
		url = "http://maps.googleapis.com/maps/api/distancematrix/json?origins=";
		url = url + coordinates + "&destinations="+ address_line + "&mode=driving&language=en-US&sensor=false";
		return url;
	}
	
	/**
	 *	Returns the time from the current location
	 * to the destination specified in address_line
	 * by performing a httprequest and parsing the
	 * resulting json. 
	 * @return int
	 */
	 public int getTime() {

		DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
		HttpPost httppost = new HttpPost("" + create_query_url());
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
	            JSONArray rows = rootObject.getJSONArray("rows"); // Get all JSONArray rows

	            for(int i=0; i < rows.length(); i++) { // Loop over each each row
	                JSONObject row = rows.getJSONObject(i); // Get row object
	                JSONArray elements = row.getJSONArray("elements"); // Get all elements for each row as an array

	                for(int j=0; j < elements.length(); j++) { // Iterate each element in the elements array
	                    JSONObject element =  elements.getJSONObject(j); // Get the element object
	                    JSONObject duration = element.getJSONObject("duration"); // Get duration sub object

	                    item1 = duration.getInt("value"); // Print int value
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
		
		//String recieved, now parsing for time value
		
		
		// format value coorectly
		//double timeInMin = Double.valueOf(new DecimalFormat("####.##").format(item1/60.0));
		return item1/60;

	}
	
}
