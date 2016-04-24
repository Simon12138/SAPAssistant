package com.seu.community;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cengalabs.flatui.views.FlatButton;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class DemoActivity extends Activity {
	
	private EditText usernameTxt;
	private EditText passwordTxt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		usernameTxt = (EditText) findViewById(R.id.login_user_name);
		passwordTxt = (EditText) findViewById(R.id.login_password);
		FlatButton submit = (FlatButton) findViewById(R.id.login_submit);
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				requestLogin(usernameTxt.getText().toString(), passwordTxt.getText().toString());
			}
		});
		
	}
	
	private void requestLogin(String userName, String password) {
		String url = "http://192.168.1.109:8080/CommunityServer/user/login";
		RequestQueue mQueue = Volley.newRequestQueue(DemoActivity.this);
		Map<String, String> appendHeader = new HashMap<String, String>();
		appendHeader.put("userName", userName);
		appendHeader.put("password", password);
		System.out.println(new JSONObject(appendHeader).toString());
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(appendHeader),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Toast.makeText(DemoActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						
					}
				});
		mQueue.add(request);
	}
}
