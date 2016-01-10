package com.sap.assistant.admin;

import com.assistant.sap.util.ShareData;

import cn.bong.android.sdk.BongManager;
import cn.bong.android.sdk.config.Environment;
import cn.bong.android.sdk.event.TouchEvent;
import cn.bong.android.sdk.event.TouchEventListener;
import cn.bong.android.sdk.model.http.auth.AuthError;
import cn.bong.android.sdk.model.http.auth.AuthInfo;
import cn.bong.android.sdk.model.http.auth.AuthUiListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class MainActivity extends Activity {

	private Button doorBtn = null;
	private Button freeFoodBtn = null;
	private Button foodBtn = null;
	private Button submit = null;

	private TextView msgShowTxt = null;
	private LinearLayout priceContainer = null;

	private EditText priceEdt = null;

	private final static int DOOR_TYPE = 0;
	private final static int FREE_FOOD_TYPE = 1;
	private final static int FOOD_TYPE = 2;
	private int type = DOOR_TYPE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		bindView();
		setListener();
	}

	private void initData() {
		BongManager.initialize(this, "1415266387250", "",
				"7d9b930cabff430a96adce868f90fc85");
		BongManager.setDebuged(true);
		BongManager.setEnvironment(Environment.Daily);
		
		if(!BongManager.isSessionValid()){
			BongManager.bongAuth(this, ShareData.AUTH_INSTANCE, new AuthUiListener() {
				
				@Override
				public void onSucess(AuthInfo arg0) {
					
				}
				
				@Override
				public void onError(AuthError arg0) {
					
				}
				
				@Override
				public void onCancel() {
					
				}
			});
		}
	}

	private void bindView() {
		doorBtn = (Button) findViewById(R.id.door_type);
		freeFoodBtn = (Button) findViewById(R.id.free_food_type);
		foodBtn = (Button) findViewById(R.id.food_type);
		msgShowTxt = (TextView) findViewById(R.id.swid_type);
		priceContainer = (LinearLayout) findViewById(R.id.price_container);
		priceEdt = (EditText) findViewById(R.id.food_price);
		submit = (Button) findViewById(R.id.price_submit);
	}

	private void setListener() {
		doorBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				type = DOOR_TYPE;
				priceEdt.setVisibility(View.INVISIBLE);
				submit.setVisibility(View.INVISIBLE);
				msgShowTxt.setBackgroundColor(getResources().getColor(
						R.color.green));
				priceContainer.setBackgroundColor(getResources().getColor(
						R.color.green));
				msgShowTxt.setText(R.string.door_type);
			}
		});
		freeFoodBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				type = FREE_FOOD_TYPE;
				priceEdt.setVisibility(View.INVISIBLE);
				submit.setVisibility(View.INVISIBLE);
				msgShowTxt.setBackgroundColor(getResources().getColor(
						R.color.pink));
				priceContainer.setBackgroundColor(getResources().getColor(
						R.color.pink));
				msgShowTxt.setText(R.string.free_food_type);
			}
		});
		foodBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				type = FOOD_TYPE;
				priceEdt.setVisibility(View.VISIBLE);
				submit.setVisibility(View.VISIBLE);
				msgShowTxt.setBackgroundColor(getResources().getColor(
						R.color.blue));
				priceContainer.setBackgroundColor(getResources().getColor(
						R.color.blue));
				msgShowTxt.setText(R.string.food_type);
			}
		});

		BongManager.turnOnTouchEventListen(MainActivity.this,
				new TouchEventListener() {

					@Override
					public void onTouch(TouchEvent event) {
						String msg = "";
						switch (type) {
						case DOOR_TYPE:
							
							msg += getResources().getString(R.string.door_type) + "\n刷卡成功";
							Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
							break;
						case FREE_FOOD_TYPE:
							msg = "";
							msg += getResources().getString(R.string.door_type) + "\n刷卡成功";
							Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
							break;
						case FOOD_TYPE:
							msg = "";
							msg += getResources().getString(R.string.door_type) + "\n刷卡成功";
							Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
							break;
						default:
							break;
						}
					}

					@Override
					public void onLongTouch(TouchEvent event) {

					}
				});
	}
}
