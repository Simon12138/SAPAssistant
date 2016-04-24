package com.seu.community;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.seu.community.adapter.ListItemClickHelp;
import com.seu.community.adapter.ShopAdapter;
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

public class AllFoodActivity extends Activity implements ListItemClickHelp{

	private List<Map<String, Object>> shops = new ArrayList<>();
	private ShopAdapter adapter = null;
	private ListView listView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_food);
		// initData();
		bindView();
		setListener();
		getDataFromServer();
	}

	private void bindView() {
		listView = (ListView) findViewById(R.id.all_shops_page_list);
		adapter = new ShopAdapter(AllFoodActivity.this, shops, this);
		listView.setAdapter(adapter);
	}
	
	private void setListener() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Intent intent = new Intent(AllFoodActivity.this, FoodListActivity.class);
				Bundle bundle = new Bundle();
				Toast.makeText(AllFoodActivity.this, shops.get(position).get("id").toString(), Toast.LENGTH_SHORT).show();
				bundle.putInt("sid", Integer.parseInt(shops.get(position).get("id").toString()));
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}

	private void getDataFromServer() {
		String url = ShareData.BASEURL + "getallshops.json";
		RequestQueue queue = Volley.newRequestQueue(AllFoodActivity.this);
		Map<String, Object> appendHeader = new HashMap<>();
		appendHeader.put("uid", 1);
		MyJsonArrayRequest request = new MyJsonArrayRequest(url,
				new JSONObject(appendHeader),
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						shops.clear();
						for (int i = 0; i < response.length(); i++) {
							try {
								JSONObject object = (JSONObject) response
										.get(i);
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("id", object.getInt("id"));
								map.put("shopname", object.getString("shopname"));
								map.put("address", object.getString("address"));
								map.put("score", object.getString("score"));
								map.put("like", object.getString("like"));
								map.put("image", object.getString("image"));
								shops.add(map);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(AllFoodActivity.this, "Çë¼ì²é±¾µØÍøÂç",
								Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(request);
	}

	@Override
	public void onClick(View item, View view, int position, int which) {
		switch (which) {
		case R.id.shop_item_like:
			clickLike(position);
			break;
		default:
			break;
		}
	}
	
	private void clickLike(int position) {
		if(shops.get(position).get("like").toString().equals("LIKE")){
			shops.get(position).remove("like");
			shops.get(position).put("like", "DISLIKE");
			sendLikeRequest("DISLIKE", Integer.parseInt(shops.get(position).get("id").toString()));
		} else {
			shops.get(position).remove("like");
			shops.get(position).put("like", "LIKE");
			sendLikeRequest("LIKE", Integer.parseInt(shops.get(position).get("id").toString()));
		}
		adapter.notifyDataSetChanged();
	}
	
	private void sendLikeRequest(String like, int sid) {
		String url = ShareData.BASEURL + "likeshop.json";
		RequestQueue mQueue = Volley.newRequestQueue(AllFoodActivity.this);
		HashMap<String, Object> appendHeader = new HashMap<>();
		appendHeader.put("uid", ShareData.UID);
		appendHeader.put("sid", sid);
		appendHeader.put("like", like.equals("LIKE"));
		JsonObjectRequest request = new JsonObjectRequest(
				Request.Method.POST, url, new JSONObject(appendHeader),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
				});
		mQueue.add(request);
		
	}
}
