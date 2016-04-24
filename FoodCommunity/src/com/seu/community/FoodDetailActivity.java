package com.seu.community;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.seu.community.adapter.CommentAdapter;
import com.seu.community.adapter.CommentDialog;
import com.seu.community.adapter.CommentDialog.OnSureClickListener;
import com.seu.community.tool.ShareData;
import com.seu.community.volley.MyJsonArrayRequest;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class FoodDetailActivity extends Activity {

	private ImageView image = null;
	private RatingBar ratingBar = null;
	private TextView score = null;
	private TextView desc = null;
	private ListView commentsListView = null;
	private EditText comment = null;
	private Button send = null;
	private int fid = -1;
	private Integer touid = null;
	
	private String commentContent;
	
	private List<Map<String, Object>> comments = new ArrayList<>();
	private CommentAdapter adapter = null;

	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food_detail);
		fid = getIntent().getIntExtra("fid", -1);
		bindingView();
		getDataFromServer();
		getCommentFromServer();
		setListener();
	}

	private void bindingView() {
		image = (ImageView) findViewById(R.id.food_detail_image);
		ratingBar = (RatingBar) findViewById(R.id.food_detail_rating_bar);
		score = (TextView) findViewById(R.id.food_detail_rating_score);
		desc = (TextView) findViewById(R.id.food_detail_desc);
		commentsListView = (ListView) findViewById(R.id.food_detail_comments);
		adapter = new CommentAdapter(FoodDetailActivity.this, comments);
		commentsListView.setAdapter(adapter);
		comment = (EditText) findViewById(R.id.food_detail_comment_text);
		send = (Button) findViewById(R.id.food_detail_comment_button);
	}
	
	private void commentFood(String content) {
		String url = ShareData.BASEURL + "commentfood.json";
		RequestQueue mQueue = Volley.newRequestQueue(FoodDetailActivity.this);
		Map<String, Object> appendHeader = new HashMap<>();
		appendHeader.put("uid", ShareData.UID);
		appendHeader.put("fid", fid);
		appendHeader.put("comment", content);
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
				url, new JSONObject(appendHeader), new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				comment.setText("");
				getCommentFromServer();
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
		});
		mQueue.add(request);
	}

	private void getDataFromServer() {
		String url = ShareData.BASEURL + "getfooddetail.json";
		RequestQueue queue = Volley.newRequestQueue(FoodDetailActivity.this);
		Map<String, Object> appendHeader = new HashMap<>();
		appendHeader.put("fid", fid);
		JsonObjectRequest request = new JsonObjectRequest(url, new JSONObject(
				appendHeader), new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {
				try {
					ShareData.getImageLoader().displayImage(
							response.getString("image"), image,
							ShareData.getOptions(), animateFirstListener);
					float scoreBar = (float) response.getDouble("score");
					ratingBar.setRating(scoreBar);
					score.setText(response.getString("score"));
					desc.setText(response.getString("desc"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(FoodDetailActivity.this, "请检查本地网络",
						Toast.LENGTH_SHORT).show();
			}
		});
		queue.add(request);
	}

	private void setListener() {
		ratingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar arg0, float rating, boolean arg2) {
				scoreFood(rating);
				
			}
		});
		commentsListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				touid = Integer.parseInt(comments.get(position).get("uid").toString());
				if(touid != ShareData.UID) {
					OnSureClickListener listener = new OnSureClickListener(){
						@Override
						public void getText(String string) {
							commentContent = string;
						}
					};
					Dialog dialog = new CommentDialog(FoodDetailActivity.this, R.style.MyDialog, listener);
					Window window = dialog.getWindow();
					window.setGravity(Gravity.CENTER);
					dialog.setCanceledOnTouchOutside(false);
					dialog.show();
					dialog.setOnCancelListener(new OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
//							commentFood(commentContent);	
							
						}
					});
					dialog.setOnKeyListener(new OnKeyListener() {
						
						@Override
						public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
							if(keyCode == KeyEvent.KEYCODE_BACK){
								dialog.dismiss();
							}
							return false;
						}
					});
				} else {
					comment.setFocusable(true);
				}
			}
		});
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				commentFood(comment.getText().toString());
			}
		});
	}
	
	private void updateScoreFromServer() {
		// get food score from food table
		String url = ShareData.BASEURL + "getscore.json";
		RequestQueue mQueue = Volley.newRequestQueue(FoodDetailActivity.this);
		Map<String, Object> appendHeader = new HashMap<>();
		appendHeader.put("fid", fid);
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, 
				url, new JSONObject(appendHeader), new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						try {
							ratingBar.setRating(Float.parseFloat(response.getString("score")));
							score.setText(response.getString("score"));
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(FoodDetailActivity.this, "请检查本地网络",
								Toast.LENGTH_SHORT).show();
					}
				});
		mQueue.add(request);
	}
	
	private void scoreFood(float rating) {
		String url = ShareData.BASEURL + "scorefood.json";
		RequestQueue mQueue = Volley.newRequestQueue(FoodDetailActivity.this);
		Map<String, Object> appendHeader = new HashMap<String, Object>();
		appendHeader.put("score", rating);
		appendHeader.put("uid", ShareData.UID);
		appendHeader.put("fid", fid);
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
				url, new JSONObject(appendHeader),
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						updateScoreFromServer();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
					}
				});
		mQueue.add(request);
	}
	
	private void getCommentFromServer() {
		String url = ShareData.BASEURL + "getcommentoffood.json";
		RequestQueue queue = Volley.newRequestQueue(FoodDetailActivity.this);
		Map<String, Object> appendHeader = new HashMap<>();
		appendHeader.put("fid", fid);
		MyJsonArrayRequest request = new MyJsonArrayRequest(url,
				new JSONObject(appendHeader),
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						comments.clear();
						for (int i = 0; i < response.length(); i++) {
							try {
								JSONObject object = (JSONObject) response
										.get(i);
								Map<String, Object> map = new HashMap<String, Object>();
								map.put("id", object.getInt("id"));
								map.put("uid", object.getInt("uid"));
								map.put("touid", object.getInt("touid"));
								map.put("content", object.getString("content"));
								comments.add(map);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						adapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(FoodDetailActivity.this, "请检查本地网络",
								Toast.LENGTH_SHORT).show();
					}
				});
		queue.add(request);
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
