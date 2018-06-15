package com.example.lenovo.transpic;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by lnnn on 2018/6/15.
 */

public class LoadingView extends View {
    private Context mContext;
    private Paint mPaint;
    private int widthSpecSize;
    private int heightSpecSize;
    private int radiusSmall = 38;
    private int radiusbig = 76;
    private int moveX;
    private int XPoint;
    private int mState = -1;//0失败，1成功，-1默认
    private boolean mflag;
    private ValueAnimator animator;

    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint = new Paint();
        mPaint.setColor(0xFFFFBC53);
        mPaint.setAntiAlias(true);
        if (Math.abs(moveX) > widthSpecSize * 5 / 4){
            XPoint = (moveX < 0) ? XPoint = widthSpecSize * 7 / 4 - Math.abs(moveX):
                    widthSpecSize - widthSpecSize * 7 / 4 + Math.abs(moveX);
            canvas.drawCircle(XPoint, heightSpecSize / 2, radiusSmall, mPaint);
        }

        if (Math.abs(moveX) > widthSpecSize && Math.abs(moveX) < widthSpecSize * 3 / 2){
            XPoint = (moveX < 0) ? XPoint = widthSpecSize * 3 / 2 - Math.abs(moveX):
                    widthSpecSize - widthSpecSize * 3 / 2 + Math.abs(moveX);
            canvas.drawCircle(XPoint, heightSpecSize / 2, radiusSmall, mPaint);
        }

        if (Math.abs(moveX) > widthSpecSize * 3 / 4 && Math.abs(moveX) < widthSpecSize * 5 / 4){
            XPoint = (moveX < 0) ? XPoint = widthSpecSize * 5 / 4 - Math.abs(moveX) :
                    widthSpecSize - widthSpecSize * 5 / 4 + Math.abs(moveX);
            canvas.drawCircle(XPoint, heightSpecSize / 2, radiusSmall, mPaint);
        }

        if (Math.abs(moveX) > widthSpecSize / 2 && Math.abs(moveX) < widthSpecSize){
            XPoint = (moveX < 0) ? XPoint = widthSpecSize - Math.abs(moveX) :
                    widthSpecSize - widthSpecSize + Math.abs(moveX);
            canvas.drawCircle(XPoint, heightSpecSize / 2, radiusSmall, mPaint);
        }

        if (Math.abs(moveX) > widthSpecSize / 4 && Math.abs(moveX) < widthSpecSize * 3 / 4){
            XPoint = (moveX < 0) ? XPoint = widthSpecSize * 3 / 4 - Math.abs(moveX) :
                    widthSpecSize - widthSpecSize * 3 / 4 + Math.abs(moveX);
            canvas.drawCircle(XPoint, heightSpecSize / 2, radiusSmall, mPaint);
        }

        if (Math.abs(moveX) > 0 && Math.abs(moveX) < widthSpecSize / 2){
            XPoint = (moveX < 0) ? XPoint = widthSpecSize / 2 - Math.abs(moveX) :
                    widthSpecSize - widthSpecSize / 2 + Math.abs(moveX);
            canvas.drawCircle(XPoint, heightSpecSize / 2, radiusSmall, mPaint);
        }

        if (Math.abs(moveX) > 0 && Math.abs(moveX) < widthSpecSize * 5 / 4){
            radiusbig = 2 * radiusSmall - radiusSmall * (Math.abs(moveX)) / (widthSpecSize * 5 / 4);
            radiusbig = (radiusbig > radiusSmall) ? radiusbig : radiusSmall;
            canvas.drawCircle(widthSpecSize / 2, heightSpecSize / 2, radiusbig, mPaint);
        }

        if (Math.abs(moveX) < 12 && mState >= 0){
            if (mState == 0){
                canvas.drawCircle(widthSpecSize / 2, heightSpecSize / 2, radiusbig, mPaint);
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.cross_circle);
                canvas.drawBitmap(bitmap, null, new Rect(
                        widthSpecSize / 2 - radiusbig,
                        heightSpecSize / 2 - radiusbig,
                        widthSpecSize / 2 + radiusbig,
                        heightSpecSize / 2 + radiusbig), mPaint);
            }

            if (mState == 1){
                canvas.drawCircle(widthSpecSize / 2, heightSpecSize / 2, radiusbig, mPaint);
                Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.checkmark_circle);
                canvas.drawBitmap(bitmap, null, new Rect(
                        widthSpecSize / 2 - radiusbig,
                        heightSpecSize / 2 - radiusbig,
                        widthSpecSize / 2 + radiusbig,
                        heightSpecSize / 2 + radiusbig), mPaint);
            }
        }
    }

    public void start(){
        if (animator != null)
            animator.cancel();
        moveX = widthSpecSize * (-9 / 4);
        mState = 1;
        mflag = true;

        post(new Runnable() {
            @Override
            public void run() {
                animator = ValueAnimator.ofFloat(0f, 1.0f);
                animator.setDuration(3000);
                animator.setRepeatMode(ValueAnimator.RESTART);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.setInterpolator(new LinearInterpolator());
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        if (mState < 0){
                            moveX = (moveX > widthSpecSize * 7 / 4) ? widthSpecSize * (-9 / 4) : moveX + 12;
                            if (Math.abs(moveX) < 12)
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }}else {
                            if (moveX > 0)
                                moveX = (moveX > widthSpecSize * 7 / 4) ? widthSpecSize * (9 / 4) : moveX + 12;
                            else if (moveX < 0 && mflag){
                                moveX += 12;
                                if (Math.abs(moveX) < 12)
                                    mflag = false;
                            }
                        }
                        postInvalidate();
                    }
                });
                animator.start();
            }
        });
    }

    public void success() {
        mState = 1;     }

    public void failed() {
        mState = 0;     }
}
