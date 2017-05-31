package com.liu.custom.customview.wight.circlemenu;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

/**
 * Created by jiemi-server on 2017/5/24.
 */

public class CircleMenuLayout extends ViewGroup {

    private int mRadius = 0;
    private static final float RADIO_DEFAULT_CHILD_DIMENSION = 1/4f;
    private static final float RADDIO_PADDING_LAYOUT = 1/12f;
    private float mPadding;

    private double mStartAngle =0 ;

    private ListAdapter mAdapter ;

    private OnItemClickListener mOnMenuItemClickListener;


    public CircleMenuLayout(Context context) {
        super(context);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPadding(0,0,0,0);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight( heightMeasureSpec);

        setMeasuredDimension(width,height);

        measureChildViews();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final  int childCount = getChildCount();
        int left,top;
        int itemWidth = (int) (mRadius*RADIO_DEFAULT_CHILD_DIMENSION);
        float angleDelay = 360/childCount;
        for (int i=0;i<childCount;++i){
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE){
                continue;
            }

            mStartAngle %=360;
            float distanceFromCenter = mRadius/2f - itemWidth/2 - mPadding;
            left = mRadius/2+(int) Math.round(distanceFromCenter*Math.cos(Math.toRadians(mStartAngle)) - 1/2f*itemWidth);
            top = mRadius/2+(int) Math.round(distanceFromCenter*Math.sin(Math.toRadians(mStartAngle)) - 1/2f*itemWidth);

            child.layout(left,top,left+itemWidth,top+itemWidth);

            mStartAngle += angleDelay;
        }

    }

    private int measureWidth(int widthMeasureSpec){
        int width = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);

        if (mode != MeasureSpec.EXACTLY){
            width = getSuggestedMinimumWidth();
            width = width ==0 ? getDefaultSize(MeasureSpec.getSize(widthMeasureSpec),mode):width;
        }
        else {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }

        return  width;
    }

    private int measureHeight(int heightMeasureSpec){

        int height = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec);

        if (mode != MeasureSpec.EXACTLY){
            height = getSuggestedMinimumWidth();
            height = height ==0 ? getDefaultSize(MeasureSpec.getSize(heightMeasureSpec),mode):height;
        }
        else {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        return  height;
    }

    private void measureChildViews(){

        mRadius = Math.max(getMeasuredWidth(),getMeasuredHeight());
        final  int count = getChildCount();
        int childSize = (int) (mRadius*RADIO_DEFAULT_CHILD_DIMENSION);
        int childMode = MeasureSpec.EXACTLY;
        for (int i=0 ;i<count;++i){
            final View child = getChildAt(i);
            if (child.getVisibility() == GONE){
                continue;
            }

            int makeMeasureSpec = -1;
            makeMeasureSpec = MeasureSpec.makeMeasureSpec(childSize,childMode);
            child.measure(makeMeasureSpec,makeMeasureSpec);
        }

        mPadding = RADDIO_PADDING_LAYOUT*mRadius;
    }

    private interface OnItemClickListener{
        public void onClick(View view,int position);
    }

    public ListAdapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(ListAdapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    private void buildMenuItems(){
        for ( int i=0;i<mAdapter.getCount();++i){
            final View itemView = mAdapter.getView(i,null,this);
            final int position = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnMenuItemClickListener !=null){
                        mOnMenuItemClickListener.onClick(v,position);
                    }
                }
            });

            addView(itemView);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        if (mAdapter !=null){
            buildMenuItems();
        }
        super.onAttachedToWindow();
    }
}
