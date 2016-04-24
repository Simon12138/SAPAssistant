package com.seu.community.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.seu.community.R;
import com.seu.community.tool.ShareData;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodAdapter extends BaseAdapter {

	private List<Map<String, Object>> foods = null;
	private LayoutInflater mInflater = null;
	
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public FoodAdapter(Context context, List<Map<String, Object>> foods) {
		mInflater = LayoutInflater.from(context);
		this.foods = foods;
	}

	@Override
	public int getCount() {
		return foods.size();
	}

	@Override
	public Object getItem(int position) {
		return foods.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		FoodItem item;
		if (view == null) {
			item = new FoodItem();
			view = mInflater.inflate(R.layout.food_item, parent, false);
			item.image = (ImageView) view.findViewById(R.id.food_item_image);
			item.name = (TextView) view.findViewById(R.id.food_item_name);
			item.price = (TextView) view.findViewById(R.id.food_item_price);
			item.description = (TextView) view
					.findViewById(R.id.food_item_desc);
			view.setTag(item);
		} else {
			item = (FoodItem) view.getTag();
		}
		item.name.setText(foods.get(position).get("name").toString());
		item.price.setText(foods.get(position).get("price").toString());
		item.description.setText(foods.get(position).get("desc").toString());
		ShareData.getImageLoader().displayImage(foods.get(position).get("image")
				.toString(), item.image, ShareData.getOptions(), animateFirstListener);
		return view;
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}

class FoodItem {
	public ImageView image;
	public TextView name;
	public TextView price;
	public TextView description;
}