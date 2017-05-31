package com.liu.custom.customview.wight;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liu.custom.customview.R;
import com.liu.custom.customview.bean.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BannerLayout extends HorizontalScrollView {

	private static final String TAG = "BannerLayout";

	private List<Banner> dataList = new ArrayList<>();

	/** 传入整体布局 */
	private LinearLayout groupLayout;

	/** 父类的活动activity */
	private Context context;

	private int itemMargin = 20;
	private int textBgColor = Color.GRAY;
	private int textColor = Color.BLACK;
	private int itemBackground = 0;

	private int Height= 0;
	private int Width= 0;

	//轮训时间间隔
	private int timerInterval = 5;
	private Timer timer;

	//0:正向  1:反向
	private int directory = 0;

	//是否触碰
	private boolean isTouch = false;
	//触碰起始位置
	private float startX =0 ;
	private float startY =0 ;

	//触碰移动最想距离
	private float Length = 20 ;

	//当前显示index
	private int CurrentIndex = 0;



	public BannerLayout(Context context) {
		super(context);
	}

	public BannerLayout(Context context, AttributeSet attrs) {

		this(context, attrs,0);
	}

	public BannerLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		init(context);
	}

	private void init(Context context){
		this.context = context;

		Width = getDisplayWidth(context) - 4*itemMargin;

		groupLayout = new LinearLayout(context);
		groupLayout.setOrientation(LinearLayout.HORIZONTAL);
		groupLayout.setGravity(LinearLayout.VERTICAL);

		this.addView(groupLayout);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	/**
	 * 在拖动的时候执行
	 * */
	@Override
	protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		// TODO Auto-generated method stub
		super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);

	}

	////////////////////////////////////////////////////////////////////////////////////////////////


	public List<Banner> getDataList() {
		return dataList;
	}

	public void setDataList(List<Banner> dataList) {
		this.dataList = dataList;
		if (dataList.size()<1){
			return;
		}

		drawView();
	}

	/**
	 *
	 */
	private void drawView(){
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Width, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.CENTER;
		if (dataList.size() == 1){
			params.setMargins(2*itemMargin,0,2*itemMargin,0);
			groupLayout.addView(createBanner(dataList.get(0)),params);
		}
		else {
			LinearLayout.LayoutParams leftParams = new LinearLayout.LayoutParams(Width, ViewGroup.LayoutParams.WRAP_CONTENT);
			leftParams.setMargins(2*itemMargin,0,itemMargin,0);
			groupLayout.addView(createBanner(dataList.get(0)),leftParams);

			params.setMargins(0,0,itemMargin,0);
			for (int i=1;i<dataList.size()-1;++i){
				groupLayout.addView(createBanner(dataList.get(i)),params);
			}
			LinearLayout.LayoutParams rightParams = new LinearLayout.LayoutParams(Width, ViewGroup.LayoutParams.WRAP_CONTENT);
			rightParams.setMargins(0,0,2*itemMargin,0);
			groupLayout.addView(createBanner(dataList.get(dataList.size()-1)),rightParams);

			timer = new Timer();
			timer.schedule(task, 1000, timerInterval*1000);
		}

		requestLayout();

//		postInvalidate();
	}

	private View createBanner(Banner banner){

		RelativeLayout layout = new RelativeLayout(context);
		layout.setGravity(Gravity.CENTER);

		RelativeLayout.LayoutParams imvParams = new RelativeLayout.LayoutParams(Width,ViewGroup.LayoutParams.MATCH_PARENT);
		ImageView img = new ImageView(context);
		img.setScaleType(ImageView.ScaleType.FIT_XY);
		img.setBackgroundColor(Color.RED);
//		img.setImageResource(R.mipmap.banner);
		Glide.with(img.getContext()).load(banner.getUrl()).placeholder(R.mipmap.banner).into(img);

		layout.addView(img,imvParams);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		TextView textView = new TextView(context);
		textView.setText(banner.getTitle());
		textView.setBackgroundColor(textBgColor);
		textView.setTextColor(textColor);
		textView.setPadding(10,10,10,10);
		layout.addView(textView,params);

		return layout;
	}

	/**
	 * 选中tab
	 * @param index
     */
	public void selectTab(int index){

		for (int i=0;i<groupLayout.getChildCount();++i){
			groupLayout.getChildAt(i).setSelected(false);
		}
		groupLayout.getChildAt(index).setSelected(true);

		scrollToIndex(index);
	}

	/**
	 * 滑动到选中tab
	 * @param index
     */
	private void scrollToIndex(int index){
		Log.i(TAG,"index:"+index);

		if (isTouch){
			return;
		}

		if (index <0 ){
			index = 0;
		}
		else if(index> dataList.size()-1){
			index = dataList.size()-1;
		}
		CurrentIndex = index;

		int pos = 0;
		if (CurrentIndex >0){
			View child = groupLayout.getChildAt(CurrentIndex-1);
			int k = child.getMeasuredWidth();
			int l = child.getLeft();
			pos = k+l - itemMargin;
		}
		else {
			pos = 0;
		}

		Log.i(TAG,"pos:"+pos);

		smoothScrollTo(pos,0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()){
			case MotionEvent.ACTION_UP:
				isTouch = false;
				float xLen = ev.getRawX() - startX;
				float yLen = ev.getRawY() - startY;
//				Log.i("msg","xLen:"+xLen + "  yLen:"+yLen);
				if (Math.abs(xLen)>Math.abs(yLen) && Math.abs(xLen) > Length){
					if (xLen>0){
						scrollToIndex(CurrentIndex - 1);
					}
					else {
						scrollToIndex(CurrentIndex + 1);
					}
				}
				startX = 0;
				startY = 0;
				break;
			case MotionEvent.ACTION_DOWN:
				isTouch = true;
				startX = ev.getRawX();
				startY = ev.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				return super.onTouchEvent(ev);
		}

//		return super.onTouchEvent(ev);
		return true;
	}

	/////////////////////////////////////对外事件接口///////////////////////////////////////////////
	public interface OnItemClickListener{
		void OnItemClick(int position);
	}

	/**
	 * The listener that receives notifications when an item is clicked.
	 */
	OnItemClickListener itemClickListener;

	public void setOnItemClickListener(OnItemClickListener listener){
		itemClickListener = listener;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 *
	 * @param context
	 * @return
	 */
	public static int getDisplayWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}

	/**
	 * 创建计时器
	 * @return
	 */
	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			// TODO Auto-generated method stub

			if (directory == 0){
				if (CurrentIndex < dataList.size() -1){
					scrollToIndex(CurrentIndex+1);
				}
				else {
					directory = 1;
					scrollToIndex(CurrentIndex-1);
				}
			}
			else {
				if (CurrentIndex >0){
					scrollToIndex(CurrentIndex-1);
				}
				else {
					directory = 0;
					scrollToIndex(CurrentIndex+1);
				}
			}

		}
	};

	/**
	 *
	 */
	public void destroy(){
		if (timer !=null){
			timer.cancel();
		}
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public int getTextBgColor() {
		return textBgColor;
	}

	public void setTextBgColor(int textBgColor) {
		this.textBgColor = textBgColor;
	}

	public int getTimerInterval() {
		return timerInterval;
	}

	public void setTimerInterval(int timerInterval) {
		this.timerInterval = timerInterval;
		timer.cancel();
		timer.schedule(task, 1000, timerInterval*1000);
	}

}
