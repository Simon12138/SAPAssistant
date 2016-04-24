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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopAdapter extends BaseAdapter {

	private List<Map<String, Object>> shops = null;
	private LayoutInflater mInflater = null;
	private ListItemClickHelp callback;

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public ShopAdapter(Context context, List<Map<String, Object>> shops, ListItemClickHelp callback) {
		mInflater = LayoutInflater.from(context);
		this.shops = shops;
		this.callback = callback;
	}

	@Override
	public int getCount() {
		return shops.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return shops.get(position);
	}

	@Override
	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View view, final ViewGroup parent) {
		ShopItem item;
		if (view == null) {
			item = new ShopItem();
			view = mInflater.inflate(R.layout.shop_item, parent, false);
			item.image = (ImageView) view.findViewById(R.id.shop_item_image);
			item.name = (TextView) view.findViewById(R.id.shop_item_name);
			item.address = (TextView) view.findViewById(R.id.shop_item_address);
			item.like = (TextView) view.findViewById(R.id.shop_item_like);
			view.setTag(item);
		} else {
			item = (ShopItem) view.getTag();
		}
		item.name.setText(shops.get(position).get("shopname").toString());
		item.address.setText(shops.get(position).get("address").toString());
		final int p = position;
		final int id = item.like.getId();
		final View v = view;
		item.like.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				callback.onClick(v, parent, p, id);
			}
		});
		if (shops.get(position).get("like").toString().equals("DISLIKE")) {
			item.like.setText("LIKE");
			item.like.setBackgroundColor(view.getResources().getColor(R.color.blue));
		} else {
			item.like.setText("DISLIKE");
			item.like.setBackgroundColor(view.getResources().getColor(R.color.yellow));
		}
		ShareData.getImageLoader().displayImage(shops.get(position).get("image")
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

class ShopItem {
	public ImageView image;
	public TextView name;
	public TextView address;
	public TextView like;
}
