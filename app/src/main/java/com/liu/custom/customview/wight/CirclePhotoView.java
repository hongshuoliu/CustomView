package com.liu.custom.customview.wight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.liu.custom.customview.R;

/**
 * Created by jiemi-server on 2017/2/27.
 */

public class CirclePhotoView extends View {

    private Bitmap bmpCat;
    private Bitmap bmpCircleMask;
    private Canvas cvsCircle;
    private Paint paint;

    public CirclePhotoView(Context context) {
        super(context);
    }

    public CirclePhotoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bmpCat = BitmapFactory.decodeResource(getResources(), R.mipmap.girl);
        int minWidth = Math.min(bmpCat.getWidth(),bmpCat.getHeight());
        bmpCircleMask = Bitmap.createBitmap(minWidth,minWidth, Bitmap.Config.ARGB_8888);

        cvsCircle = new Canvas(bmpCircleMask);
        int r = minWidth/2;
        cvsCircle.drawCircle(r,r,r,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = bmpCircleMask.getWidth();
        int layer = canvas.saveLayer(0,0,w,w,null, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bmpCat,0,0,null);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bmpCircleMask,0,0,paint);
        canvas.restoreToCount(layer);
    }
}
