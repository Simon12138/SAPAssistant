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
import com.seu.community.adapter.UserAdapter;
import com.seu.community.tool.ShareData;
import com.seu.community.volley.MyJsonArrayRequest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class UserHomeActivity extends Activity implements ListItemClickHelp{
	
	private TextView nameTextView = null;
	private TextView emailTextView = null;
	private TextView myFriend = null;
	private TextView allFriend = null;
	private ListView friendsList = null;
	private UserAdapter adapter = null;
	
	private List<Map<String, Object>> friends = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home);
		bindView();
		setListener();
		getInfoFromServer();
		getMyFriendsFromServer();
	}
	
	private void setListener() {
		myFriend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				myFriend.setTextColor(getResources().getColor(R.color.white));
				myFriend.setBackgroundColor(getResources().getColor(R.color.yellow));
				allFriend.setTextColor(getResources().getColor(R.color.black));
				allFriend.setBackgroundColor(getResources().getColor(R.color.grey));
				getMyFriendsFromServer();
			}
		});
		allFriend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				allFriend.setTextColor(getResources().getColor(R.color.white));
				allFriend.setBackgroundColor(getResources().getColor(R.color.yellow));
				myFriend.setTextColor(getResources().getColor(R.color.black));
				myFriend.setBackgroundColor(getResources().getColor(R.color.grey));
				getAllFriendsFromServer();
			}
		});
	}
	
	private void bindView() {
		nameTextView = (TextView) findViewById(R.id.user_home_name);
		emailTextView = (TextView) findViewById(R.id.user_home_email);
		myFriend = (TextView) findViewById(R.id.user_home_my_friend);
		allFriend = (TextView) findViewById(R.id.user_home_all_friend);
		friendsList = (ListView) findViewById(R.id.user_home_friend_list);
		adapter = new UserAdapter(UserHomeActivity.this, friends, this);
		friendsList.setAdapter(adapter);
	}
	
	private void getInfoFromServer() {
		String url = ShareData.BASEURL + "getuserinfo.json";
		RequestQueue mQueue = Volley.newRequestQueue(UserHomeActivity.this);
		Map<String, Object> appendHeader = new HashMap<>();
		appendHeader.put("uid", ShareData.UID);
		JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(appendHeader),
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							nameTextView.setText(response.getString("name"));
							emailTextView.setText(response.getString("email"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
				});
		mQueue.add(request);
	}
	
	private void getMyFriendsFromServer() {
		String url = ShareData.BASEURL + "getmyfriends.json";
		RequestQueue mQueue = Volley.newRequestQueue(UserHomeActivity.this);
		Map<String, Object> appendHeader = new HashMap<>();
		appendHeader.put("uid", ShareData.UID);
		MyJsonArrayRequest request = new MyJsonArrayRequest(url, new JSONObject(appendHeader),
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						friends.clear();
						for(int i = 0; i< response.length(); i++){
							JSONObject object;
							try {
								object = (JSONObject) response.get(i);
								Map<String, Object> map = new HashMap<>();
								map.put("id", object.getInt("id"));
								map.put("name", object.getString("name"));
								map.put("my", "DEL");
								map.put("email", object.getString("email"));
								friends.add(map);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
				});
		mQueue.add(request);
	}
	
	private void getAllFriendsFromServer() {
		String url = ShareData.BASEURL + "getallfriends.json";
		RequestQueue mQueue = Volley.newRequestQueue(UserHomeActivity.this);
		Map<String, Object> appendHeader = new HashMap<>();
		appendHeader.put("uid", ShareData.UID);
		MyJsonArrayRequest request = new MyJsonArrayRequest(url, new JSONObject(appendHeader),
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						friends.clear();
						for(int i = 0; i< response.length(); i++){
							JSONObject object;
							try {
								object = (JSONObject) response.get(i);
								Map<String, Object> map = new HashMap<>();
								map.put("id", object.getInt("id"));
								map.put("name", object.getString("name"));
								map.put("my", "ADD");
								map.put("email", object.getString("email"));
								friends.add(map);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
				});
		mQueue.add(request);
	}

	@Override
	public void onClick(View item, View view, int position, int which) {
		switch (which) {
		case R.id.friend_item_add:
			clickAdd(position);
			break;
		default:
			break;
		}
	}
	
	private void sendAddRequest(String add, int uid) {
		String url;
		if(add.equals("ADD")) {
			url = ShareData.BASEURL + "addfriend.json";
		} else {
			url = ShareData.BASEURL + "delfriend.json";
		}
		RequestQueue mQueue = Volley.newRequestQueue(UserHomeActivity.this);
		HashMap<String, Object> appendHeader = new HashMap<>();
		appendHeader.put("uid", ShareData.UID);
		appendHeader.put("touid", uid);
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
	
	private void clickAdd(int position) {
		if(friends.get(position).get("my").toString().equals("DEL")){
			sendAddRequest("DEL", Integer.parseInt(friends.get(position).get("id").toString()));
			friends.remove(position);
		} else {
			sendAddRequest("ADD", Integer.parseInt(friends.get(position).get("id").toString()));
			friends.remove(position);
		}
		adapter.notifyDataSetChanged();
	}
}
