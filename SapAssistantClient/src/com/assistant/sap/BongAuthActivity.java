package com.assistant.sap;

import com.assistant.sap.util.ShareData;

import cn.bong.android.sdk.BongManager;
import cn.bong.android.sdk.model.http.auth.AuthError;
import cn.bong.android.sdk.model.http.auth.AuthInfo;
import cn.bong.android.sdk.model.http.auth.AuthUiListener;
import cn.bong.android.sdk.utils.DialogUtil;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class BongAuthActivity extends Activity {

	private Button obtain_authBtn = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bong_auth);
		obtain_authBtn = (Button) findViewById(R.id.bong_auth_btn);
		obtain_authBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!BongManager.isSessionValid()) {
					BongManager.bongAuth(BongAuthActivity.this,
							ShareData.AUTH_INSTANCE, new AuthUiListener() {
								@Override
								public void onSucess(AuthInfo result) {
									// go to company login activity
								}

								@Override
								public void onError(AuthError error) {
									DialogUtil.showTips(BongAuthActivity.this,
											"Auth Failed", " code  : " + error.code
													+ "\nmsg   : "
													+ error.message
													+ "\ndetail: "
													+ error.errorDetail);
								}

								@Override
								public void onCancel() {
									DialogUtil.showTips(BongAuthActivity.this,
											"Tips", "Auth canceled");
								}
							});
				} else {
					// go to company login activity
				}
			}
		});
	}
}
