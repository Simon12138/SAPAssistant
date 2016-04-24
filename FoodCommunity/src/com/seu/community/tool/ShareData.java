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
				.showStubImage(R.drawable.goodsloading) // ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.goodsloading) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.goodsfail) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
				// .displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
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
