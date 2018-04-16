package com.canvasdemo.cc.canvasdemo;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by CC on 2018/4/16.
 */

public class ClockView extends View {

    private Paint mPaint;
    private int mCenterX;
    private int mCenterY;
    private Paint mDegreePaint;
    private Paint mHourPaint;
    private Paint mMinPaint;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        //初始化画笔
        mPaint = new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        //初始化刻度的画笔
        mDegreePaint = new Paint();
        mDegreePaint.setColor(Color.BLACK);
        mDegreePaint.setStrokeWidth(10);
        //初始化时针和分针画笔
        mHourPaint = new Paint();
        mHourPaint.setStrokeWidth(15);
        mHourPaint.setStrokeCap(Paint.Cap.ROUND);
        mMinPaint = new Paint();
        mMinPaint.setStrokeWidth(10);
        mMinPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制一个圆
        canvas.drawCircle(mCenterX, mCenterY, mCenterX, mPaint);
        //绘制刻度
        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                //长刻度
                canvas.drawLine(mCenterX,mCenterY-mCenterX,mCenterX,mCenterY-mCenterX+60,mDegreePaint);
                //文字
                mDegreePaint.setTextSize(30);
                String s = String.valueOf(i);
                canvas.drawText(s,mCenterX-mDegreePaint.measureText(s)/2,mCenterY-mCenterX+90,mDegreePaint);
            }else {
                mDegreePaint.setStrokeWidth(5);
                mDegreePaint.setTextSize(15);
                canvas.drawLine(mCenterX,mCenterY-mCenterX,mCenterX,mCenterY-mCenterX+30,mDegreePaint);
                String s = String.valueOf(i);
                canvas.drawText(s,mCenterX-mDegreePaint.measureText(s)/2,mCenterY-mCenterX+60,mDegreePaint);
            }
            canvas.rotate(15,mCenterX,mCenterY);
        }
        //绘制时针和分针
        //平移画布来绘制分针和时针的圆点
        canvas.save();
        canvas.translate(mCenterX,mCenterY);
        canvas.drawLine(0,0,100,100,mHourPaint);
        canvas.drawLine(0,0,100,200,mMinPaint);
        canvas.restore();
    }
}
