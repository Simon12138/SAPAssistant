package com.assistant.sap;


import com.alibaba.fastjson.JSONArray;
import com.assistant.sap.util.ShareData;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText login_userNameEdt = null;
	private EditText login_passwordEdt = null;
	private Button login_submitBtn = null;
	private Button login_clearBtn = null;
	
	private JSONArray results;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		bindView();
		
		setListener();
		
	}
	
	private void bindView(){
		login_userNameEdt = (EditText) findViewById(R.id.login_user_name);
		login_passwordEdt = (EditText) findViewById(R.id.login_password);
		login_submitBtn = (Button) findViewById(R.id.login_button);
		login_clearBtn = (Button) findViewById(R.id.clear_button);
	}
	
	private void initData(){
		if(login_passwordEdt.getText().toString().equals("Admin") && 
				login_passwordEdt.getText().toString().equals("123456")){
			results = JSONArray.parseArray("[{\"code\" : 200, \"message\" : \"login success!\"},{\"uid\" : 1}]");
		} else {
			results = JSONArray.parseArray("[{\"code\" : 201, \"message\" : \"username or password is wrong!\"}]");
		}
	}
	
	private void setListener(){
		login_submitBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				initData();
				if(results.getJSONObject(0).getIntValue("code") == 200){
					Toast.makeText(LoginActivity.this, results.getJSONObject(0).getString("message"), 
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					ShareData.UID = results.getJSONObject(1).getIntValue("uid");
					startActivity(intent);
				}
			}
		});
		login_clearBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				login_userNameEdt.setText("");
				login_passwordEdt.setText("");
			}
		});
		
	}
}
