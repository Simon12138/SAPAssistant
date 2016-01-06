package com.assistant.sap.adapter;

import java.util.List;
import java.util.Map;

import com.assistant.sap.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkSpaceListAdapter extends BaseAdapter{
	private List<Map<String, Object>> items;
	private LayoutInflater mInflater = null;
	
	public WorkSpaceListAdapter(Context context, List<Map<String, Object>> items) {
		mInflater = LayoutInflater.from(context);
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		WorkSpaceItem item = null;
		if (view == null) {
			item = new WorkSpaceItem();
			view = mInflater.inflate(R.layout.work_space_item, parent,false);
			item.startTimeTxt = (TextView) view.findViewById(R.id.workspace_item_meeting_time);
			item.lineView = (View) view.findViewById(R.id.workspace_item_line);
			item.checkBox = (ImageView) view.findViewById(R.id.workspace_item_check_box);
			item.subjectTxt = (TextView) view.findViewById(R.id.workspace_item_detail_subject);
			item.locationTxt = (TextView) view.findViewById(R.id.workspace_item_detail_location);
			item.remainTimeTxt = (TextView) view.findViewById(R.id.workspace_item_remain_time);
			view.setTag(item);
		} else {
			item = (WorkSpaceItem) view.getTag();
		}
		item.startTimeTxt.setText(items.get(position).get("starttime").toString());
		if((Boolean)(items.get(position).get("check"))){
			item.checkBox.setBackgroundResource(R.drawable.ic_launcher);
		}else{
			item.checkBox.setBackgroundResource(R.drawable.ic_launcher);
		}
		item.subjectTxt.setText(items.get(position).get("subject").toString());
		item.locationTxt.setText(items.get(position).get("location").toString());
		item.remainTimeTxt.setText(items.get(position).get("remaintime").toString());
		return view;
	}

}

class WorkSpaceItem {
	public TextView startTimeTxt;
	public View lineView;
	public ImageView checkBox;
	public TextView subjectTxt;
	public TextView locationTxt;
	public TextView remainTimeTxt;
}
