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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	private TextView sexMaleTxt = null;
	private TextView sexFemaleTxt = null;
	private EditText userNameEdt = null;
	private EditText passwordEdt = null;
	private EditText rePasswordEdt = null;
	private EditText emailEdt = null;

	private TextView toolBarBack = null;
	private TextView toolBarTitle = null;
	private TextView toolBarSubmit = null;

	private int sex = ShareData.MALE;
	private Handler handler = null;
	private final static int MSG_UN_REPEAT_PASSWORD = 1;
	private final static int MSG_REGISTER_SUCCESS = 2;
	private final static int MSG_ERROR_INTERNET = 3;
	private final static int MSG_NULL_FIELD = 4;
	private final static int MSG_REGISTER_FAILED = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		bindView();
		setIconToNormal();
		setListener();
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if (msg.what == MSG_UN_REPEAT_PASSWORD) {
					Toast.makeText(RegisterActivity.this, "密码不一致，请重新输入",
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == MSG_REGISTER_SUCCESS || msg.what == MSG_REGISTER_FAILED) {
					Toast.makeText(RegisterActivity.this, "注册成功！",
							Toast.LENGTH_SHORT).show();
					finish();
				} else if (msg.what == MSG_ERROR_INTERNET) {
					Toast.makeText(RegisterActivity.this, "请检查本地网络",
							Toast.LENGTH_SHORT).show();
				} else if (msg.what == MSG_NULL_FIELD) {
					Toast.makeText(RegisterActivity.this, "所有选项均为必填项目",
							Toast.LENGTH_SHORT).show();
				}
			}
		};
	}

	private void bindView() {
		sexMaleTxt = (TextView) findViewById(R.id.register_page_sex_male);
		sexFemaleTxt = (TextView) findViewById(R.id.register_page_sex_female);
		userNameEdt = (EditText) findViewById(R.id.register_page_user_name);
		passwordEdt = (EditText) findViewById(R.id.register_page_password);
		rePasswordEdt = (EditText) findViewById(R.id.register_page_re_password);
		emailEdt = (EditText) findViewById(R.id.register_page_email);
		toolBarBack = (TextView) findViewById(R.id.toobar_back);
		toolBarTitle = (TextView) findViewById(R.id.toobar_title);
		toolBarTitle.setText("用户注册");
		toolBarSubmit = (TextView) findViewById(R.id.toobar_submit);
	}

	private void setIconToNormal() {
		sexMaleTxt.setBackgroundColor(getResources().getColor(R.color.yellow));
		sexFemaleTxt.setBackgroundColor(getResources().getColor(R.color.grey));
		sexMaleTxt.setTextColor(getResources().getColor(R.color.white));
		sexFemaleTxt.setTextColor(getResources().getColor(R.color.black));
	}

	private void setListener() {
		sexMaleTxt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				setIconToNormal();
				sex = ShareData.MALE;
			}
		});
		sexFemaleTxt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				sexMaleTxt.setBackgroundColor(getResources().getColor(
						R.color.grey));
				sexFemaleTxt.setBackgroundColor(getResources().getColor(
						R.color.yellow));
				sexMaleTxt.setTextColor(getResources().getColor(R.color.black));
				sexFemaleTxt.setTextColor(getResources()
						.getColor(R.color.white));
				sex = ShareData.FEMALE;
			}
		});
		toolBarSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (userNameEdt.getText().toString().equals("")
						|| passwordEdt.getText().toString().equals("")
						|| emailEdt.getText().toString().equals("")) {
					Message msg = new Message();
					msg.what = MSG_NULL_FIELD;
					handler.sendMessage(msg);
					return;
				}
				registerUser();
			}
		});
		toolBarBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void registerUser() {
		String userName = userNameEdt.getText().toString();
		String password = passwordEdt.getText().toString();
		String rePassword = rePasswordEdt.getText().toString();
		String email = emailEdt.getText().toString();
		Message msg = new Message();
		if (!password.equals(rePassword)) {
			msg.what = MSG_UN_REPEAT_PASSWORD;
			handler.sendMessage(msg);
			return;
		}
		String url = ShareData.BASEURL + "register.json";
		RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
		Map<String, Object> appendHeader = new HashMap<String, Object>();
		appendHeader.put("sex", sex);
		appendHeader.put("userName", userName);
		appendHeader.put("password", password);
		appendHeader.put("email", email);
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
				url, new JSONObject(appendHeader),
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							Message msg = new Message();
							if(response.getString("flag").equals("ok")){
								msg.what = MSG_REGISTER_SUCCESS;
							} else {
								msg.what = MSG_REGISTER_FAILED;
							}
							handler.sendMessage(msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Message msg = new Message();
						msg.what = MSG_ERROR_INTERNET;
						handler.sendMessage(msg);
					}
				});
		queue.add(request);
	}
}
