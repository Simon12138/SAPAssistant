package com.seu.community.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.seu.community.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends BaseAdapter {

	private List<Map<String, Object>> comments = null;
	private LayoutInflater mInflater = null;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public CommentAdapter(Context context, List<Map<String, Object>> comments) {
		mInflater = LayoutInflater.from(context);
		this.comments = comments;
	}

	@Override
	public int getCount() {
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		CommentItem item;
		if (view == null) {
			item = new CommentItem();
			view = mInflater.inflate(R.layout.food_comment_item, parent, false);
			item.imageView = (ImageView) view
					.findViewById(R.id.food_comment_image);
			item.comment = (TextView) view
					.findViewById(R.id.food_comment_content);
			view.setTag(item);
		} else {
			item = (CommentItem) view.getTag();
		}
//		ShareData.getImageLoader().displayImage(
//				comments.get(position).get("image").toString(), item.imageView,
//				ShareData.getOptions(), animateFirstListener);
		item.comment.setText(comments.get(position).get("content").toString());
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
class CommentItem {
	public ImageView imageView;
	public TextView comment;
}
