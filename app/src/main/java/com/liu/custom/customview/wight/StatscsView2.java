package com.liu.custom.customview.wight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by jiemi-server on 2017/4/21.
 */

public class StatscsView2 extends View {

    //  坐标轴 轴线 画笔：
    private Paint axisLinePaint;
    //  坐标轴水平内部 虚线画笔
    private Paint hLinePaint;
    //  绘制文本的画笔
    private Paint titlePaint;
    //  矩形画笔 柱状图的样式信息
    private Paint recPaint;
    private int rectWidth = 20;

    private int startX = 100;
    private int minStep = 100;

    private int xStep = 0;
    private int yStep = 0;

    private int xLength = 700;
    private int yLength = 500;

    private int xMargin = 10;
    private int yMargin = 30;

    private int maxValue = 1000;

    private int xFontSize = 20;
    private int yFontSize = 20;

    private int rectColor = Color.parseColor("#1078CF");

    //
    private List<Integer> list1 = new ArrayList<>();
    private List<String> yTitles = new ArrayList<>();
    private List<String> xTitles = new ArrayList<>();

    public StatscsView2(Context context) {
        this(context,null);
    }

    public StatscsView2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        // TODO Auto-generated constructor stub
    }

    public StatscsView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    private void init()
    {
        axisLinePaint = new Paint();
        hLinePaint = new Paint();
        titlePaint = new Paint();
        recPaint = new Paint();

        axisLinePaint.setColor(Color.DKGRAY);
        hLinePaint.setColor(Color.LTGRAY);
        titlePaint.setColor(Color.BLACK);
        recPaint.setColor(rectColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

//        xLength = width - xMargin;
        xStep = (xLength-xMargin)/list1.size();
        yStep = (yLength-yMargin)/list1.size();

        Log.i("msg","width:"+width+"  height:"+height);

        // Y坐标线：
        canvas.drawLine(startX, 0, startX, yLength, axisLinePaint);
        // X坐标线：
        canvas.drawLine(startX, yLength, xLength+startX , yLength, axisLinePaint);
        Log.i("msg","x:"+startX + " y:"+yLength+ " len:"+xLength);

        drawLine(canvas);

        drawYLable(canvas);

        drawXLable(canvas);

        drawHistogram(canvas);

    }

    // 4  绘制 X 周 做坐标
    private void drawXLable(Canvas canvas){

        for(int i=0;i<xTitles.size();i++){
            canvas.drawText(xTitles.get(i), startX+xStep*(i+1), yLength +xMargin +xFontSize, titlePaint);
        }
    }


    // 2 绘制坐标内部的水平线
    private void drawLine(Canvas canvas){

        hLinePaint.setTextAlign(Paint.Align.CENTER);
        for(int i=0;i<yTitles.size();i++){
            canvas.drawLine(startX, yStep*(i)+yMargin, xLength+startX, yStep*(i)+yMargin, hLinePaint);
        }
    }


    // 3 绘制 Y 周坐标
    private void drawYLable(Canvas canvas){

        titlePaint.setTextAlign(Paint.Align.RIGHT);
        titlePaint.setTextSize(30);
        for(int i=0;i<yTitles.size();i++){
            canvas.drawText(yTitles.get(i), startX, yStep*(yTitles.size() - i-1) + yFontSize/2+yMargin, titlePaint);
        }
    }

    // 5 绘制矩形
    private void drawHistogram(Canvas canvas){

        if (list1 == null || list1.size()<1){
            return;
        }

        for(int i=0;i<list1.size();i++)
        {
            int height = (yLength-yMargin) * list1.get(i) / maxValue ;
//            Log.i("msg","he:"+height +" len:"+yLength);

            Rect rect = new Rect();
            rect.left  = startX + xStep * (i+1)  - rectWidth;
            rect.right =  rect.left+rectWidth;
            rect.top = yLength - height;
            rect.bottom = yLength;
//            Log.i("msg","left:"+rect.left +" right:"+rect.right+ " top"+rect.top + "  bottom"+ rect.bottom);

            canvas.drawRect(rect, recPaint);
        }

    }

    public void setData(List<Integer> list,List<String> yLabels , List<String> xLabels){

        if (list.isEmpty()){
            return;
        }
        list1.clear();
        list1.addAll(list);

        Collections.reverse(list); //倒序排列 获取最大值
        maxValue = list.get(0);

        if (xLabels.isEmpty()){
            for (int i=0;i<list1.size();++i){
                this.xTitles.add(String.valueOf(i+1));
            }
        }
        else {
            this.xTitles.clear();
            this.xTitles.addAll(xLabels);
        }

        if (yLabels.isEmpty()){

            int tmp = maxValue/list1.size();
            for (int i=0;i<list1.size();++i){
                this.yTitles.add(String.valueOf(tmp*(i+1)));
            }
        }
        else {
            this.yTitles.clear();
            this.yTitles.addAll(yLabels);
        }


        this.postInvalidate();  //可以子线程 更新视图的方法调用。
    }

}
