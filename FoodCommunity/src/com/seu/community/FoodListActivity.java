package com.seu.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.seu.community.adapter.FoodAdapter;
import com.seu.community.tool.ShareData;
import com.seu.community.volley.MyJsonArrayRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FoodListActivity extends Activity {
	
	private List<Map<String, Object>> foods = new ArrayList<>();
	private FoodAdapter adapter = null;
	private ListView listView = null;
	private int shop_id = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_list);
		shop_id = getIntent().getIntExtra("sid", 0);
		bindView();
		setListener();
		getDataFromServer();
	}
	
	private void bindView() {
		listView = (ListView) findViewById(R.id.shop_foods_page_list);
		adapter = new FoodAdapter(FoodListActivity.this, foods);
		listView.setAdapter(adapter);
	}
	
	private void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				Intent intent = new Intent(FoodListActivity.this, FoodDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("fid", Integer.parseInt(foods.get(position).get("id").toString()));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
	private void getDataFromServer() {
		String url = ShareData.BASEURL + "getfoodsofshop.json";
		RequestQueue queue = Volley.newRequestQueue(FoodListActivity.this);
		Map<String, Object> appendHeader = new HashMap<>();
		// shop id
		appendHeader.put("sid", shop_id);
		MyJsonArrayRequest request = new MyJsonArrayRequest(url,
				new JSONObject(appendHeader),
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						foods.clear();
						for (int i = 0; i < response.length(); i++) {
							try {
								JSONObject object = (JSONObject) response
										.get(i);
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("id", object.getInt("id"));
								map.put("name", object.getString("name"));
								map.put("price", object.getString("price"));
								map.put("score", object.getString("score"));
								map.put("desc", object.getString("desc"));
								map.put("image", object.getString("image"));
								foods.add(map);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(FoodListActivity.this, "Çë¼ì²é±¾µØÍøÂç",
								Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(request);
	}
}
