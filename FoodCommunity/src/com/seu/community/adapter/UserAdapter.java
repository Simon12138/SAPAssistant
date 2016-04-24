package com.seu.community.adapter;

import java.util.List;
import java.util.Map;

import com.seu.community.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserAdapter extends BaseAdapter {
	private List<Map<String, Object>> friends = null;
	private LayoutInflater mInflater = null;
	private ListItemClickHelp callback;
	
	public UserAdapter(Context context, List<Map<String, Object>> friends, ListItemClickHelp callback) {
		mInflater = LayoutInflater.from(context);
		this.friends = friends;
		this.callback = callback;
	}

	@Override
	public int getCount() {
		return friends.size();
	}

	@Override
	public Object getItem(int position) {
		return friends.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, final ViewGroup parent) {
		FriendItem item;
		if (view == null) {
			item = new FriendItem();
			view = mInflater.inflate(R.layout.friend_item, parent, false);
			item.name = (TextView) view.findViewById(R.id.friend_item_name);
			item.email = (TextView) view.findViewById(R.id.friend_item_email);
			item.add = (TextView) view.findViewById(R.id.friend_item_add);
			view.setTag(item);
		} else {
			item = (FriendItem) view.getTag();
		}
		item.name.setText(friends.get(position).get("name").toString());
		item.email.setText(friends.get(position).get("email").toString());
		final int p = position;
		final int id = item.add.getId();
		final View v = view;
		item.add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				callback.onClick(v, parent, p, id);
			}
		});
		if (friends.get(position).get("my").toString().equals("ADD")) {
			item.add.setText("ADD");
			item.add.setBackgroundColor(view.getResources().getColor(R.color.blue));
		} else {
			item.add.setText("DEL");
			item.add.setBackgroundColor(view.getResources().getColor(R.color.blue));
		}
		return view;
	}

}

class FriendItem {
	public TextView name;
	public TextView email;
	public TextView add;
}
