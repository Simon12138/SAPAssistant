package com.assistant.sap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.assistant.sap.adapter.WorkSpaceListAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class WorkSpaceListActivity extends Activity {
	
	private ListView listView = null;
	private WorkSpaceListAdapter adapter = null;
	private List<Map<String, Object>> items = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_work_space_list);
		bindView();
		initData();
	}
	
	private void bindView(){
		listView = (ListView) findViewById(R.id.workspace_items_list_view);
		items = new ArrayList<Map<String,Object>>();
		adapter = new WorkSpaceListAdapter(WorkSpaceListActivity.this, items);
		listView.setAdapter(adapter);
	}
	
	private void initData(){
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("starttime", "09:00");
		map1.put("check", true);
		map1.put("subject", "Discuss about Sap Assistant");
		map1.put("location", "MR PVG02 C3.2");
		map1.put("remaintime", "23min");
		items.add(map1);
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("starttime", "11:00");
		map2.put("check", false);
		map2.put("subject", "Meeting For Decoupling");
		map2.put("location", "MR PVG02 C3.1");
		map2.put("remaintime", "2h more");
		items.add(map2);
		
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("starttime", "14:00");
		map3.put("check", false);
		map3.put("subject", "Drink Tea Together");
		map3.put("location", "MR PVG03 C3.1");
		map3.put("remaintime", "5h more");
		items.add(map3);
		adapter.notifyDataSetChanged();
	}
}
