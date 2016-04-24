package com.seu.community;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {

	private LinearLayout all_shops = null;
	private LinearLayout comment_shops = null;
	private LinearLayout comment_foods = null;
	private TextView homepage = null;
	
	private TabHost tabHost = null;
	
	private final static int ALL_SHOPS_IDX = 1;
	private final static int COMMENT_SHOPS_IDX = 2;
	private final static int COMMENT_FOODS_IDX = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindView();
		setListener();
		setIconToSpecial(ALL_SHOPS_IDX);
	}
	
	private void bindView() {
		doTabHost();
		all_shops = (LinearLayout) findViewById(R.id.all_shops_page);
		comment_shops = (LinearLayout) findViewById(R.id.comment_shops_page);
		comment_foods = (LinearLayout) findViewById(R.id.comment_foods_page);
		homepage = (TextView) findViewById(R.id.toobar_submit);
	}
	private void setListener() {
		all_shops.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setIconToSpecial(ALL_SHOPS_IDX);
				tabHost.setCurrentTabByTag("所有商店");
			}
		});
		
		comment_shops.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setIconToSpecial(COMMENT_SHOPS_IDX);
				tabHost.setCurrentTabByTag("推荐商店");
			}
		});
		
		comment_foods.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setIconToSpecial(COMMENT_FOODS_IDX);
				tabHost.setCurrentTabByTag("推荐食品");
			}
		});
		homepage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this, UserHomeActivity.class);
				startActivity(intent);
			}
		});
	}
	
	private void doTabHost(){
        tabHost = this.getTabHost();
        TabHost.TabSpec spec;
        Intent intent;
        intent = new Intent().setClass(this, AllFoodActivity.class);
        spec = tabHost.newTabSpec("所有商店").setIndicator("所有商店").setContent(intent);
        tabHost.addTab(spec);
        intent = new Intent().setClass(this, CommentShopsActivity.class);
        spec = tabHost.newTabSpec("推荐商店").setIndicator("推荐商店").setContent(intent);
        tabHost.addTab(spec);
        intent = new Intent().setClass(this, CommentFoodsActivity.class);
        spec = tabHost.newTabSpec("推荐食品").setIndicator("推荐食品").setContent(intent);
        tabHost.addTab(spec);

        tabHost.setCurrentTabByTag("所有商店");
    }
	
	private void setIconToCommon(){
		all_shops.setBackgroundColor(getResources().getColor(R.color.grey));
		comment_shops.setBackgroundColor(getResources().getColor(R.color.grey));
		comment_foods.setBackgroundColor(getResources().getColor(R.color.grey));
	}
	
	private void setIconToSpecial(int index) {
        switch (index) {
            case ALL_SHOPS_IDX:
                setIconToCommon();
                all_shops.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case COMMENT_SHOPS_IDX:
                setIconToCommon();
                comment_shops.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
            case COMMENT_FOODS_IDX:
                setIconToCommon();
                comment_foods.setBackgroundColor(getResources().getColor(R.color.blue));
                break;
        }
    }
}
