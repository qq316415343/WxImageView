package org.cn.cnitr.electronic.quanchenmoments.ui.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import org.cn.cnitr.electronic.quanchenmoments.R;
import org.cn.cnitr.electronic.quanchenmoments.utils.CommonUtils;

/**
 * Created by longzhiwei on 2017/8/17.
 */

public class WxImageView extends ImageView {

    private Context context;
    private float boultSize;
    private Path path;
    private Paint paint;

    private int radius = 5;
    private int type;  //0向左 1向右

    private int color;
    private float border;

    private boolean click;

    private GestureDetector myGesture;

    public WxImageView(Context context) {
        this(context, null);
    }

    public WxImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WxImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WxImageView);
        boultSize = a.getDimension(R.styleable.WxImageView_boultSize, dpTopx(25f));
        border = a.getDimension(R.styleable.WxImageView_border, 0.0f);
        color = a.getColor(R.styleable.WxImageView_borderColor, Color.WHITE);
        a.recycle();

        
//        myGesture = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
//            @Override
//            public boolean onDown(MotionEvent motionEvent) {
//                click = true;
//                invalidate();
//                return false;
//            }
//
//            @Override
//            public void onShowPress(MotionEvent motionEvent) {
//
//            }
//
//            @Override
//            public boolean onSingleTapUp(MotionEvent motionEvent) {
//                click = false;
//                invalidate();
//                return false;
//            }
//
//            @Override
//            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//                click = false;
//                invalidate();
//                return false;
//            }
//
//            @Override
//            public void onLongPress(MotionEvent motionEvent) {
//
//            }
//
//            @Override
//            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
//                click = false;
//                invalidate();
//                return false;
//            }
//        });
    }

    private int dpTopx(float dp){
        return CommonUtils.getInstance().Dp2Px(context, dp);
    }

    private void init() {
        path = new Path();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        paint.setColor(color);
        paint.setStrokeWidth(border);
//        path.moveTo(0, dpTopx(radius));
//        path.arcTo(new RectF(0,0,dpTopx(radius),dpTopx(radius)),180,90);
//        path.lineTo(w - dpTopx(radius), 0);
//        path.arcTo(new RectF(0,0,dpTopx(radius),dpTopx(radius)),-90,90);
//        //箭头向左
//        path.lineTo(w, h - dpTopx(radius));
//
//        path.arcTo(new RectF(0,0,dpTopx(radius),dpTopx(radius)),0,90);
//        path.lineTo(dpTopx(radius), h);
//        path.arcTo(new RectF(0,0,dpTopx(radius),dpTopx(radius)),90,90);
//        path.lineTo(0, h - dpTopx(radius));
//        path.close();

        switch (type) {
            case 0:
                initLeftPath();
                break;
            case 1:
                initRightPath();
                break;
        }
    }

    private void initLeftPath(){
        int w = getWidth() - ((int)border * 2);
        int h = getHeight() - ((int)border * 2);

        int radiusPx = dpTopx(radius);
        int radiusPx2 = radiusPx *2;

        int boultWidth = dpTopx(6.0f); //箭头的宽度

//        Path path = new Path();
        path.moveTo(boultWidth + border, radiusPx + border);
        path.arcTo(new RectF(boultWidth + border, 0 + border, radiusPx2 + boultWidth + border, radiusPx2 + border), 180, 90);
        path.lineTo(w - radiusPx, border);
        path.arcTo(new RectF(w - radiusPx2, border, w, radiusPx2 + border), -90, 90);
        path.lineTo(w, h - radiusPx);
        path.arcTo(new RectF(w - radiusPx2, h - radiusPx2, w, h), 0, 90);
        path.lineTo(boultWidth + radiusPx + border, h);
        path.arcTo(new RectF(boultWidth + border, h - radiusPx2, radiusPx2 + boultWidth + border, h), 90, 90);

        path.lineTo(boultWidth + border, boultSize + boultWidth);
        path.lineTo(border, boultSize + boultWidth / 2);
        path.lineTo(boultWidth + border, boultSize);
        path.close();
    }

    private int startX;
    private int startY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                startY = (int) event.getY();
                click = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(event.getX() - startX) > dpTopx(2) || Math.abs(event.getY() - startY) > dpTopx(2)) {
                    //判断为滑动
                    click = false;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                click = false;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                click = false;
                invalidate();
                break;
        }
//        myGesture.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private void initRightPath() {
        int w = getWidth() - ((int)border * 2);
        int h = getHeight() - ((int)border * 2);

        int radiusPx = dpTopx(radius);
        int radiusPx2 = radiusPx *2;

        int boultWidth = dpTopx(6.0f); //箭头的宽度

//        Path path = new Path();
        path.moveTo(border, radiusPx + border);
        path.arcTo(new RectF(border, border, radiusPx2 + border, radiusPx2 + border), 180, 90);
        path.lineTo(w - radiusPx - boultWidth, border);
        path.arcTo(new RectF(w - boultWidth - radiusPx2, border, w - boultWidth, radiusPx2 + border), -90, 90);
        path.lineTo(w - boultWidth, boultSize);
        path.lineTo(w, boultSize + boultWidth / 2);
        path.lineTo(w - boultWidth, boultSize + boultWidth);
        path.lineTo(w - boultWidth, h - radiusPx);
        path.arcTo(new RectF(w - boultWidth - radiusPx2, h - radiusPx2, w - boultWidth, h), 0, 90);
        path.lineTo(radiusPx + border, h);
        path.arcTo(new RectF(border, h - radiusPx2, radiusPx2 + border, h), 90, 90);

        path.close();
    }

    private Bitmap getBitmap(Bitmap bitmap){
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.save();
        canvas.drawPath(path, paint);
        canvas.restore();

//        canvas.restore();
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return output;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        init();

        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
//        paint.reset();
        Bitmap srcBitmap = ((BitmapDrawable) drawable).getBitmap();
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        float scale = Math.max(measuredWidth * 1f / width, measuredHeight * 1f / height);
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, 0, width, height, matrix, true);
//        Bitmap blankBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

//        Bitmap newBitmap = getBitmap(bitmap);
        canvas.drawPath(path, paint);
//        path.setFillType(Path.FillType.WINDING);
        canvas.clipPath(path);
        canvas.drawBitmap(bitmap, 0, 0, null);
        if (click) {
            canvas.drawColor(0x33000000);
        }else{
            canvas.drawColor(0x00000000);
        }

//        if (!bitmap.isRecycled()) {
//            bitmap.recycle();
//        }
    }

    public void leftOrRight(int type){
        this.type = type;
    }
}
