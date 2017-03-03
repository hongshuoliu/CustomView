package com.liu.custom.customview.wight;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liu.custom.customview.R;

import java.util.ArrayList;
import java.util.List;

public class HorizontalTabLayout extends HorizontalScrollView {

	private static final String TAG = "ItemHorizontalScrollView";

	private List<String> dataList = new ArrayList<>();

	/** 传入整体布局 */
	private LinearLayout groupLayout;

	/** 父类的活动activity */
	private Context context;

	/** 可视最大tab数量*/
	private int visibleCount = 7;

	private int itemMargin = 30;
	private int backgroundColor = Color.TRANSPARENT;
	private int itemBackground = 0;
	private int itemTextColor = 0;

	public HorizontalTabLayout(Context context) {
		super(context);
	}

	public HorizontalTabLayout(Context context, AttributeSet attrs) {

		this(context, attrs,0);
	}

	public HorizontalTabLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		init(context);
	}

	private void init(Context context){
		this.context = context;
		this.itemBackground = R.drawable.selector_item_bg;
		this.itemTextColor = R.color.selector_item_text;

		groupLayout = new LinearLayout(context);
		groupLayout.setOrientation(LinearLayout.HORIZONTAL);
		groupLayout.setGravity(LinearLayout.VERTICAL);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		params.setMargins(20,0,0,10);
		this.addView(groupLayout,params);
	}

	/**
	 * 在拖动的时候执行
	 * */
	@Override
	protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
		// TODO Auto-generated method stub
		super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
		// shade_ShowOrHide();

	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 获取可见文本数量
	 * @return
	 */
	public int getVisibleCount() {
		return visibleCount;
	}

	/**
	 * 设置可见文本数量
	 * @param visibleCount
	 */
	public void setVisibleCount(int visibleCount) {
		this.visibleCount = visibleCount;
	}

	/**
	 *
	 * @return
     */
	public List<String> getDataList(){
		return  this.dataList;
	}

	public void setDataList(List<String> list){
		this.dataList = list;

		drawView();
	}

	/***
	 *
	 * @param margin
     */
	public void setItemMargin(int margin){
		itemMargin = margin;
		drawView();
	}

	public void setBackgroundColor(int color){
		this.backgroundColor = color;
		drawView();
	}

	public void setItemBackground(int resouceId){
		this.itemBackground = resouceId;
		drawView();
	}

	public void setItemTextColor(int resouceId){
		this.itemTextColor = resouceId;
		drawView();
	}


	/**
	 *
	 */
	private void drawView(){
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.setMargins(0,0,itemMargin,0);
		params.gravity = Gravity.CENTER_VERTICAL;

		for (int i=0;i<dataList.size() && i<visibleCount;++i){
			final int index = i;

			TextView textView = new TextView(context);
			textView.setText(dataList.get(index));
			textView.setTextColor(getResources().getColorStateList(itemTextColor));
			textView.setBackgroundResource(itemBackground);
			textView.setPadding(10,5,10,5);
			textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					tabClickListener.OnTabClick(v,index);

					selectTab(index);
				}
			});

			groupLayout.addView(textView,params);
		}

		requestLayout();

		/*this.postInvalidate();
        this.invalidate();
        this.requestLayout();*/
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

		scrollToTab(index);
	}

	/**
	 * 滑动到选中tab
	 * @param index
     */
	private void scrollToTab(int index){
		View child = groupLayout.getChildAt(index);
		int k = child.getMeasuredWidth();
		int l = child.getLeft();
		int pos = l + k / 2 - getWidth()/ 2;

		smoothScrollTo(pos,0);
	}



	/////////////////////////////////////对外事件接口///////////////////////////////////////////////
	public interface OnTabClickListener{
		void OnTabClick(View view, int position);
	}

	/**
	 * The listener that receives notifications when an item is clicked.
	 */
	OnTabClickListener tabClickListener;

	public void setOnTabClickListener(OnTabClickListener listener){
		tabClickListener = listener;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////

}
