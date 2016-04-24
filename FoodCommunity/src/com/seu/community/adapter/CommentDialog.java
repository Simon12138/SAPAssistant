package com.seu.community.adapter;

import com.seu.community.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentDialog extends Dialog {

	private Context context;
	private ImageView exit = null;
	private ImageView submit = null;
	private TextView image1 = null;
	private TextView image2 = null;
	private TextView image3 = null;
	private TextView image4 = null;
	private TextView image5 = null;
	private TextView result = null;
	private int score = 0;
	private EditText editText = null;
	public OnSureClickListener mListener;

	public CommentDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CommentDialog(Context context, int theme,
			OnSureClickListener listener) {
		super(context, theme);
		this.context = context;
		mListener = listener;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.comment_dialog);
		exit = (ImageView) findViewById(R.id.comment_dialog_quit);
		submit = (ImageView) findViewById(R.id.comment_dialog_submit);
		editText = (EditText) findViewById(R.id.comment_dialog_comment);
		image1 = (TextView) findViewById(R.id.comment_dialog_score_image1);
		image2 = (TextView) findViewById(R.id.comment_dialog_score_image2);
		image3 = (TextView) findViewById(R.id.comment_dialog_score_image3);
		image4 = (TextView) findViewById(R.id.comment_dialog_score_image4);
		image5 = (TextView) findViewById(R.id.comment_dialog_score_image5);
		result = (TextView) findViewById(R.id.comment_dialog_score_result);
		exit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String commentContent = editText.getText().toString();
				if (commentContent.isEmpty()) {
					Toast.makeText(context, "璇疯緭鍏ヨ瘎璁哄唴瀹广?", Toast.LENGTH_LONG)
							.show();
				} else {
					mListener.getText(editText.getText().toString());
					cancel();
				}
			}
		});

		image1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				score = 1;
				image1.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image2.setBackgroundColor(arg0.getResources().getColor(R.color.blue));
				image3.setBackgroundColor(arg0.getResources().getColor(R.color.blue));
				image4.setBackgroundColor(arg0.getResources().getColor(R.color.blue));
				image5.setBackgroundColor(arg0.getResources().getColor(R.color.blue));
				result.setText("等级:没用");
			}
		});
		image2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				score = 2;
				image1.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image2.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image3.setBackgroundColor(arg0.getResources().getColor(R.color.blue));
				image4.setBackgroundColor(arg0.getResources().getColor(R.color.blue));
				image5.setBackgroundColor(arg0.getResources().getColor(R.color.blue));
				result.setText("等级:参考");
			}
		});
		image3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				score = 3;
				image1.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image2.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image3.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image4.setBackgroundColor(arg0.getResources().getColor(R.color.blue));
				image5.setBackgroundColor(arg0.getResources().getColor(R.color.blue));
				result.setText("等级:参考");
			}
		});
		image4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				score = 4;
				image1.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image2.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image3.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image4.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image5.setBackgroundColor(arg0.getResources().getColor(R.color.blue));
				result.setText("等级:有用");
			}
		});
		image5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				score = 5;
				image1.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image2.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image3.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image4.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				image5.setBackgroundColor(arg0.getResources().getColor(R.color.yellow));
				result.setText("等级:有用");
			}
		});

	}

	public interface OnSureClickListener {
		void getText(String string); // 澹版槑鑾峰彇EditText涓暟鎹殑鎺ュ彛
	}
}
