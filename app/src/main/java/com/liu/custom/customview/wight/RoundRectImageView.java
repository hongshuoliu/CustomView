package com.liu.custom.customview.wight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.liu.custom.customview.R;

/**
 * Created by jiemi-server on 2017/2/17.
 */

public class RoundRectImageView extends ImageView {

    private final String TAG = "RoundRectImageView";

    private Paint paint;
    private Xfermode xfermode;
    private Path path = new Path();
    private int border;
    private int borderColor;
    private int radiusX;
    private int radiusY;

    public RoundRectImageView(Context context) {
        super(context);
    }

    public RoundRectImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundRectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        path = new Path();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundRectImageView);
        radiusX = a.getDimensionPixelSize(R.styleable.RoundRectImageView_rect_radiux,0);
        radiusY = a.getDimensionPixelSize(R.styleable.RoundRectImageView_rect_radiuy,0);

        border = a.getDimensionPixelSize(R.styleable.RoundRectImageView_rect_border,0);
        borderColor = a.getColor(R.styleable.RoundRectImageView_rect_border_color, Color.TRANSPARENT);
        a.recycle();
        setScaleType(ScaleType.CENTER_INSIDE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (drawable == null){
            super.onDraw(canvas);
        }

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        RectF ovalRect = new RectF(0,0,width,height);
        int layerId = canvas.saveLayer(getPaddingLeft(),getPaddingTop(),width,height,null, Canvas.ALL_SAVE_FLAG);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        canvas.drawBitmap(bitmap,new Rect(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight()),ovalRect,null);
        paint.setXfermode(xfermode);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        path.reset();
        //path.addOval(ovalRect,Path.Direction.CCW);
        path.addRoundRect(ovalRect,radiusX,radiusY, Path.Direction.CCW);
        canvas.drawPath(path,paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layerId);

        if(border != 0){
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setStrokeWidth(border);
            ovalRect.inset(border/2,border/2);
            //canvas.drawOval(ovalRect,paint);
            canvas.drawRoundRect(ovalRect,radiusX,radiusY,paint);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
