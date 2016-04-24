package com.seu.community;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.seu.community.tool.ShareData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText userNameEditText = null;
	private EditText passwordEditText = null;
	private Button loginButton = null;
	private Button registerButton = null;

	private final static int LOGIN = 0;
	private final static int ERROR = 1;
	private final static int LOGIN_F = 2;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		bindView();
		setListener();
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Toast.makeText(LoginActivity.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
				if (msg.what == LOGIN) {
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				}
			}

		};
	}

	private void bindView() {
		userNameEditText = (EditText) findViewById(R.id.login_page_user_name);
		passwordEditText = (EditText) findViewById(R.id.login_page_password);
		loginButton = (Button) findViewById(R.id.login_page_login_button);
		registerButton = (Button) findViewById(R.id.login_page_register_button);
	}

	private void setListener() {
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String userName = userNameEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				requestLogin(userName, password);
			}
		});
		registerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(LoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	private void requestLogin(String userName, String password) {
		String url = ShareData.BASEURL + "login.json";
		RequestQueue mQueue = Volley.newRequestQueue(LoginActivity.this);
		Map<String, String> appendHeader = new HashMap<String, String>();
		appendHeader.put("userName", userName);
		appendHeader.put("password", password);
		System.out.println(new JSONObject(appendHeader).toString());
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
				url, new JSONObject(appendHeader),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Message msg = new Message();
						try {
							if (response.getString("flag").equals("ok")) {
								msg.what = LOGIN;
								msg.obj = "登录成功！";
								ShareData.UID = response.getInt("uid");
								ShareData.USERNAME = response
										.getString("username");
							} else {
								msg.what = LOGIN_F;
								msg.obj = "用户名或密码错误";
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

						handler.sendMessage(msg);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Message msg = new Message();
						msg.what = ERROR;
						msg.obj = "请检查手机网络";
						handler.sendMessage(msg);
					}
				});
		mQueue.add(request);
	}
}
