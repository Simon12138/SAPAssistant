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
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class CommentFoodsActivity extends Activity {

	private List<Map<String, Object>> commentFoods = new ArrayList<>();
	private FoodAdapter adapter = null;
	private ListView listView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_foods);
		// initData();
		getDataFromServer();
		bindView();
	}

	private void bindView() {
		listView = (ListView) findViewById(R.id.comment_foods_page_list);
		adapter = new FoodAdapter(CommentFoodsActivity.this, commentFoods);
		listView.setAdapter(adapter);
	}

	private void getDataFromServer() {
		String url = ShareData.BASEURL + "getpartfood.json";
		RequestQueue queue = Volley.newRequestQueue(CommentFoodsActivity.this);
		Map<String, Object> appendHeader = new HashMap<>();
		appendHeader.put("uid", ShareData.UID);
		MyJsonArrayRequest request = new MyJsonArrayRequest(url,
				new JSONObject(appendHeader),
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						commentFoods.clear();
						for (int i = 0; i < (response.length() > 9 ? 10 : response.length()); i++) {
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
								commentFoods.add(map);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(CommentFoodsActivity.this, "Çë¼ì²é±¾µØÍøÂç",
								Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(request);
	}
}
