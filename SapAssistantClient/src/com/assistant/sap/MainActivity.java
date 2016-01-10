package com.assistant.sap;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.assistant.sap.adapter.LunchImagesAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private JSONArray workspaceResults = null;

	private TextView meetingAmountTxt = null;
	private TextView nextMeetingTimeTxt = null;
	private TextView nextMeetingSubjectTxt = null;
	private LinearLayout workspaceModule = null;

	private ViewPager lunchViewPager = null;
	private LunchImagesAdapter adapter = null;
	private ViewGroup lunchViewGroup = null;
	private List<ImageView> tips = new ArrayList<ImageView>();
	private List<ImageView> mImageViews = new ArrayList<ImageView>();
	private List<Integer> imgIds = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bindView();
		initData();
		adapter = new LunchImagesAdapter(mImageViews);
		lunchViewPager.setAdapter(adapter);
		lunchViewPager.setCurrentItem((mImageViews.size()) * 100);		
		setListener();
	}

	private void bindView() {
		// ----------------workspace module start------------------
		meetingAmountTxt = (TextView) findViewById(R.id.main_workspace_meeting_amount);
		nextMeetingTimeTxt = (TextView) findViewById(R.id.main_workspace_next_meetingtime);
		nextMeetingSubjectTxt = (TextView) findViewById(R.id.main_workspace_next_meeting);
		workspaceModule = (LinearLayout) findViewById(R.id.main_workspace_meeting_container);
		// ----------------workspace module end------------------

		lunchViewGroup = (ViewGroup) findViewById(R.id.lunch_viewGroup);
		lunchViewPager = (ViewPager) findViewById(R.id.lunch_viewPager);
	}

	private void initData() {
		// ----------------workspace module start------------------
		workspaceResults = JSONArray
				.parseArray("[{\"code\" : 200, \"message\" : \"get success!\"}, "
						+ "{\"meeting_amount\" : 3, \"next_meeting_subject\" : \"Discuss About Sap Assistant\","
						+ " \"next_meeting_start_time\" : \"17:00\", \"next_meeting_end_time\" : \"18:00\"}]");
		if (workspaceResults.getJSONObject(0).getIntValue("code") == 200) {
			meetingAmountTxt.setText(workspaceResults.getJSONObject(1)
					.getIntValue("meeting_amount")+"");
			String nextMeetingTime = "";
			nextMeetingTime += workspaceResults.getJSONObject(1).getString(
					"next_meeting_start_time");
			nextMeetingTime += "~";
			nextMeetingTime += workspaceResults.getJSONObject(1).getString(
					"next_meeting_end_time");
			nextMeetingTimeTxt.setText(nextMeetingTime);
			nextMeetingSubjectTxt.setText(workspaceResults.getJSONObject(1)
					.getString("next_meeting_subject"));
		} else {
			Toast.makeText(MainActivity.this,
					workspaceResults.getJSONObject(0).getString("message"),
					Toast.LENGTH_SHORT).show();
		}

		// ----------------workspace module end------------------

		imgIds.add(R.drawable.item01);
		imgIds.add(R.drawable.item02);
		imgIds.add(R.drawable.item03);
		imgIds.add(R.drawable.item04);
		imgIds.add(R.drawable.item05);
		imgIds.add(R.drawable.item06);

		for (int i = 0; i < imgIds.size(); i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(10, 10));
			tips.add(imageView);
			if (i == 0) {
				tips.get(i).setBackgroundResource(
						R.drawable.page_indicator_focused);
			} else {
				tips.get(i).setBackgroundResource(
						R.drawable.page_indicator_unfocused);
			}
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 5;
			layoutParams.rightMargin = 5;
			lunchViewGroup.addView(imageView, layoutParams);
		}

		for (int i = 0; i < imgIds.size(); i++) {
			ImageView imageView = new ImageView(this);
			mImageViews.add(imageView);
			imageView.setBackgroundResource(imgIds.get(i));
		}
	}

	private void setListener() {
		// ----------------workspace module start------------------
		workspaceModule.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,
						WorkSpaceListActivity.class);
				startActivity(intent);
			}
		});
		// ----------------workspace module end------------------

		lunchViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				setImageBackground(position % mImageViews.size());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.size(); i++) {
			if (i == selectItems) {
				tips.get(i).setBackgroundResource(
						R.drawable.page_indicator_focused);
			} else {
				tips.get(i).setBackgroundResource(
						R.drawable.page_indicator_unfocused);
			}
		}
	}
}
