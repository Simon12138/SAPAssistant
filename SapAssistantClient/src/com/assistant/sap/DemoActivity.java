package com.assistant.sap;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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
	private String request_url = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        
        bindView();
        
        setListener();
        
    }
    
    private void bindView(){
    	login_name_edt = (EditText) findViewById(R.id.login_name);
    	login_pwd_edt = (EditText) findViewById(R.id.login_pwd);
    	login_submit_btn = (Button) findViewById(R.id.login_submit);
    }
    
    private void setListener(){
    	login_submit_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				//如果没有服务器，该部分的代码不会起效果的
				loginRequest(login_name_edt.getText().toString(), 
						login_pwd_edt.getText().toString());
			}
		});
    }
    
    private void loginRequest(String login_name, String login_pwd){
    	RequestQueue mQueue = Volley.newRequestQueue(DemoActivity.this);
    	request_url = ShareData.BASE_SERVER + "login.json";
    	Map<String, String> appendHeader = new HashMap<String, String>();
    	appendHeader.put("username", login_name);
    	appendHeader.put("password", login_pwd);
    	VolleyRequest volleyRequest = new VolleyRequest(request_url,
    			new JSONObject(appendHeader), 
    			new Response.Listener<JSONArray>(){
					@Override
					public void onResponse(JSONArray response) {
						System.out.println("解析获得的结果并作相应的处理");
						Toast.makeText(DemoActivity.this, 
								"你可以使用handler做异步处理", Toast.LENGTH_SHORT).show();
					}
    	}, new Response.ErrorListener(){
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
