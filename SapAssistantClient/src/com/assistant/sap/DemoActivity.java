package com.assistant.sap;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.bong.android.sdk.BongManager;
import cn.bong.android.sdk.config.Environment;
import cn.bong.android.sdk.model.http.auth.AuthError;
import cn.bong.android.sdk.model.http.auth.AuthInfo;
import cn.bong.android.sdk.model.http.auth.AuthUiListener;
import cn.bong.android.sdk.utils.DialogUtil;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.assistant.sap.util.ShareData;
import com.assistant.sap.util.VolleyRequest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DemoActivity extends Activity {
	private EditText login_name_edt = null;
	private EditText login_pwd_edt = null;
	private Button login_submit_btn = null;

	private Button shouquan_btn = null;
	private Button zhendong_btn = null;

	private String request_url = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);

		initData();

		bindView();

		setListener();

	}

	private void initData() {
		BongManager.initialize(this, "1415266387250", "",
				"7d9b930cabff430a96adce868f90fc85");
		BongManager.setDebuged(true);
		BongManager.setEnvironment(Environment.Daily);
	}

	private void refreshButton() {
		if (BongManager.isSessionValid()) {
			shouquan_btn.setText("取消授权");
		} else {
			shouquan_btn.setText("开始授权");
		}
	}

	private void bindView() {
		login_name_edt = (EditText) findViewById(R.id.login_name);
		login_pwd_edt = (EditText) findViewById(R.id.login_pwd);
		login_submit_btn = (Button) findViewById(R.id.login_submit);

		shouquan_btn = (Button) findViewById(R.id.shouquan_btn);
		zhendong_btn = (Button) findViewById(R.id.zhengdong_btn);
	}

	private void setListener() {
		login_submit_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 如果没有服务器，该部分的代码不会起效果的
				loginRequest(login_name_edt.getText().toString(), login_pwd_edt
						.getText().toString());
			}
		});

		shouquan_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (BongManager.isSessionValid()) {
					// 取消授权
					BongManager.bongClearAuth();
					refreshButton();
				} else {
					// 开始授权
					BongManager.bongAuth(DemoActivity.this, ShareData.AUTH_INSTANCE,
							new AuthUiListener() {
								@Override
								public void onError(AuthError error) {
									DialogUtil.showTips(DemoActivity.this,
											"授权失败", " code  : " + error.code
													+ "\nmsg   : "
													+ error.message
													+ "\ndetail: "
													+ error.errorDetail);
								}

								@Override
								public void onSucess(AuthInfo result) {
									DialogUtil
											.showTips(
													DemoActivity.this,
													"授权成功",
													" state : "
															+ result.state
															+ "\ntoken : "
															+ result.accessToken
															+ "\nexpire: "
															+ result.expiresIn
															+ "\nuid   : "
															+ result.uid
															+ "\nscope : "
															+ result.scope
															+ "\nrefreh_expire: "
															+ result.refreshTokenExpiration
															+ "\nrefreh_token : "
															+ result.refreshToken
															+ "\ntokenType    : "
															+ result.tokenType);
									refreshButton();
								}

								@Override
								public void onCancel() {
									DialogUtil.showTips(DemoActivity.this,
											"提示", "授权取消");
								}
							});

				}

			}
		});

		zhendong_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				BongManager.bongVibrate(4, null);
				Toast.makeText(DemoActivity.this, BongManager.getUid() + "\n" + BongManager.getAccessToken(), 
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void loginRequest(String login_name, String login_pwd) {
		RequestQueue mQueue = Volley.newRequestQueue(DemoActivity.this);
		request_url = ShareData.BASE_SERVER + "login.json";
		Map<String, String> appendHeader = new HashMap<String, String>();
		appendHeader.put("username", login_name);
		appendHeader.put("password", login_pwd);
		VolleyRequest volleyRequest = new VolleyRequest(request_url,
				new JSONObject(appendHeader),
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						System.out.println("解析获得的结果并作相应的处理");
						Toast.makeText(DemoActivity.this, "你可以使用handler做异步处理",
								Toast.LENGTH_SHORT).show();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println("解析获得的结果并作相应的处理");
					}
				});
		mQueue.add(volleyRequest);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.demo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
