package com.udindev.canvasviewapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;


public class MyCanvasView extends View {

    private final Paint mPaint;
    private final Path mPath;
    private final int mDrawColor;
    private final int mBackgroundColor;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private Rect mFrame;

    public MyCanvasView(Context context) {
        this(context, null);

    }

    public MyCanvasView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mBackgroundColor=ResourcesCompat.getColor(getResources(),
                R.color.teal_200, null);
        mDrawColor=ResourcesCompat.getColor(getResources(),
                R.color.purple_200, null);

        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(mDrawColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(mBackgroundColor);

        int inset=30;
        mFrame=new Rect(inset, inset, w-inset, h-inset);
        mCanvas.drawRect(mFrame, mPaint);
    }

    //update ketika ada perubahan
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0,0,null);
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private void touchStart(float x, float y){
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchMove(float x, float y){
        float dx = Math.abs(x-mX);
        float dy = Math.abs(y-mY);
        if(dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
            mPath.quadTo(mX,mY,(x+mX)/2, (y+mY)/2);
            mX = x;
            mY = y;
            mCanvas.drawPath(mPath, mPaint);
        }
    }

    private void touchEnd(){
        mPath.reset();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchEnd();
                break;
            default:
        }

        return true;

    }
    public void setColor(int color){
        mPaint.setColor(color);
    }

    public void setStroke(float width){
        mPaint.setStrokeWidth(width);
    }
}