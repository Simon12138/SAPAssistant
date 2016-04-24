package com.seu.community.tool;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.seu.community.R;


public class ShareData {
	private final static String IP = "192.168.1.110";
	private final static String PORT = "8080";
	private final static String SERVER = "FoodComServer";
	
	public final static String BASEURL = "http://" + IP + ":" + PORT + "/" + SERVER + "/";
	public static int UID = -1;
	public static String USERNAME = "";
	
	public static final int MALE = 0;
	public static final int FEMALE = 1;
	
	public static DisplayImageOptions getOptions() {
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.goodsloading) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.goodsloading) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.goodsfail) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build();
		return options;
	}
	
	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
		public static final boolean IS_RUNNING = false;
	}
	public static ImageLoader getImageLoader() {
		return ImageLoader.getInstance();
	}
}
