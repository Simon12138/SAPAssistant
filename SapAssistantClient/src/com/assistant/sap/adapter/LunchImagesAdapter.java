package com.assistant.sap.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class LunchImagesAdapter extends PagerAdapter {
	
	private List<ImageView> mImageViews;
	
	public LunchImagesAdapter(List<ImageView> mImageViews){
		this.mImageViews = mImageViews;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	@Override  
    public void destroyItem(View container, int position, Object object) {  
        ((ViewPager)container).removeView(mImageViews.get(position % mImageViews.size()));  
          
    }
	
	@Override  
    public Object instantiateItem(View container, int position) {  
        ((ViewPager)container).addView(mImageViews.get(position % mImageViews.size()), 0);  
        return mImageViews.get(position % mImageViews.size());
    }

}
