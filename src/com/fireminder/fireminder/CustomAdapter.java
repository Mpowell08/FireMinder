package com.fireminder.fireminder;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<String>{
	private final Activity context;
	private final String[] names;
	
	static class ViewHolder {
		public TextView text;
		public TextView text2;
		public EditText edittext;
	}
	
	public CustomAdapter(Activity context, String[] names) {
		super(context, R.layout.rowlayout, names);
		this.context = context;
		this.names = names;
	}
	
	@Override 
	public View getView(int position, View convertView, ViewGroup parent){
		View rowView = convertView;
		if(rowView == null){
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.rowlayout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) rowView.findViewById(R.id.TextView01);
			viewHolder.text2 = (TextView) rowView.findViewById(R.id.time_chosen);
			viewHolder.edittext = (EditText) rowView.findViewById(R.id.EditText01);
			rowView.setTag(viewHolder);
		}
		ViewHolder holder = (ViewHolder) rowView.getTag();
		String s = names[position];
		holder.text.setText(s);
		if(s.startsWith("Arrival Time")){
			holder.edittext.setVisibility(View.GONE);
			holder.text2.setText("12:30 PM");
		} else if(s.startsWith("Date")){
			holder.edittext.setVisibility(View.GONE);
		}
		return rowView;
		
	}

}
