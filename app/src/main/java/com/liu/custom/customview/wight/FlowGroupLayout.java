package com.liu.custom.customview.wight;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liu.custom.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liu on 2017/2/21.
 */

public class FlowGroupLayout extends ViewGroup {

    private static final String TAG = "ScanHisLayout";

    /////////////////////////////////////对外事件接口///////////////////////////////////////////////
    public interface ItemClickListener{

        public void OnItemClickListener(View view, int position);
    }

    private ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////

    private List<String> dataList = new ArrayList<>();
    private Context context;
    /** 可视最大tab数量*/
    private int visibleCount = 50;

    private int itemBackground = 0;
    private int itemTextColor = 0;

    public FlowGroupLayout(Context context) {
        this(context,null);
    }

    public FlowGroupLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowGroupLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.itemBackground = R.drawable.selector_item_bg;
        this.itemTextColor = R.color.selector_item_text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG,"onDraw");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureChildren(widthMeasureSpec,heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int maxViewHeight = 0; //当前行的子组件的最大高度
        int maxLineWidth = 0; //当前行的子组件的总宽度
        int totalHeight = 0;  //累计高度
        int width = getMeasuredWidth(); 	//容器宽度

        for (int i=0;i<getChildCount() && i<visibleCount;++i){
            View child =getChildAt(i);
            MarginLayoutParams layoutParams = (MarginLayoutParams)getChildAt(i).getLayoutParams();
            int leftMargin = layoutParams.leftMargin;
            int rightMargin = layoutParams.rightMargin;
            int topMargin = layoutParams.topMargin;
            int bottomMargin = layoutParams.bottomMargin;

            if (maxLineWidth+getChildAt(i).getMeasuredWidth()>width-getPaddingLeft()-getPaddingRight()){
                totalHeight += maxViewHeight+topMargin+bottomMargin;
                maxLineWidth = 0;
                maxViewHeight = 0;
            }

            layoutChild(child,maxLineWidth+leftMargin,
                    totalHeight+topMargin,
                    maxLineWidth+child.getMeasuredWidth()+leftMargin,
                    totalHeight+child.getMeasuredHeight()+topMargin);
            maxViewHeight = Math.max(maxViewHeight,child.getMeasuredHeight());
            maxLineWidth +=child.getMeasuredWidth()+leftMargin+rightMargin;
        }

        Log.i(TAG,"maxViewHeight:"+maxViewHeight + " maxLineWidth"+maxLineWidth);
    }

    /**
     * 定位子组件，方法内考虑 padding
     * @param child
     * @param l
     * @param t
     * @param r
     * @param b
     */
    private void layoutChild(View child,int l,int t,int r,int b){

        child.layout(l+getPaddingLeft(),
                t+getPaddingTop(),
                r+getPaddingLeft(),b+getPaddingTop());
    }

    /**
     *
     * @param widthMeasureSpec
     * @return
     */
    private int measureWidth(int widthMeasureSpec){
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);

        return size;
    }

    /**
     *
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec){
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        if (mode == MeasureSpec.EXACTLY){
            height = size;
        }
        else if(mode == MeasureSpec.AT_MOST){
            int width = getMeasuredWidth();
            int childCount= getChildCount();
            if (childCount >0){
                height += getChildHeight(getChildAt(0));
            }

            int	maxLineWidth	=	0;//当前行的子组件的总宽度
            for (int i=0;i<childCount && i<visibleCount;++i){
                View child = getChildAt(i);

                if (maxLineWidth + getChildWidth(child) > width-getPaddingLeft()-getPaddingRight()){
                    height += getChildHeight(child);
                    maxLineWidth =0;
                }
                else {
                    maxLineWidth += getChildWidth(child);
                }
            }

            height += this.getPaddingTop()+getPaddingBottom();
        }
        Log.i(TAG,"height:"+height);
        return height;
    }

    /**
     * 获取子控件高度
     * @param child
     * @return
     */
    private int getChildHeight(View child){

        MarginLayoutParams layoutParams = (MarginLayoutParams)child.getLayoutParams();
        int topMargin = layoutParams.topMargin;
        int bottomMargin = layoutParams.bottomMargin;

        return child.getMeasuredHeight()+topMargin+bottomMargin;
    }

    /**
     * 获取子控件宽度
     * @param child
     * @return
     */
    private int getChildWidth(View child){

        MarginLayoutParams layoutParams = (MarginLayoutParams)child.getLayoutParams();
        int leftMargin = layoutParams.leftMargin;
        int rightMargin = layoutParams.rightMargin;

        return child.getMeasuredWidth()+leftMargin+rightMargin;
    }


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
     * 返回当前文本字段集
     * @return
     */
    public List<String> getDataList(){
        return  this.dataList;
    }

    /**
     * 设置显示文字列表
     * @param list
     */
    public void setDataList(List<String> list){
        this.dataList = list;

        for(String str:dataList){
            addView(str);
        }

        postInvalidate();
        //invalidate();

        Log.i(TAG,"list-size:"+dataList.size());
    }

    /**
     * 添加显示文字字段
     * @param str
     */
    public void addData(String str){
        this.dataList.add(str);
        addView(str);
    }

    /**
     * 在布局中添加文字标签
     * @param str
     */
    private void addView(final String str){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,30,10);
        params.gravity = Gravity.CENTER_VERTICAL;
        TextView textView = new TextView(context);
        textView.setText(str);
        textView.setTextColor(getResources().getColorStateList(R.color.white));
        textView.setBackgroundResource(R.drawable.selector_item_bg);
        textView.setPadding(30,20,30,20);
        //textView.setMaxEms(6);
        textView.setSingleLine(true);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = dataList.indexOf(str);
                itemClickListener.OnItemClickListener(v,index);
            }
        });
        this.addView(textView,params);
    }


}
