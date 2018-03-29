package com.goldmantis.wb.viewdemo.view.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.goldmantis.wb.viewdemo.utils.DisplayUtil;

/**
 * Created by Administrator on 2018/3/22.
 */

public class ClockView extends View {

    private int mWidthSize,mHeightSize;
    private int mWM,mHM;
    private Paint paint;
    private Context mContext;

    public ClockView(Context context) {
        super(context);
        init(context);
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        paint.setColor(Color.YELLOW);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int screenWidth = DisplayUtil.getDisplayWidth((Activity) mContext);
        int screenHeight = DisplayUtil.getDisplayHeight((Activity) mContext);
        mWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        mWM = MeasureSpec.getMode(widthMeasureSpec);
        mHeightSize = MeasureSpec.getSize(heightMeasureSpec);
        mHM = MeasureSpec.getMode(heightMeasureSpec);
        if (mWM == MeasureSpec.AT_MOST && mHM == MeasureSpec.AT_MOST){
            mWidthSize = screenWidth;
            mHeightSize = screenWidth;
        }else if (mWM == MeasureSpec.AT_MOST){
            if (mHeightSize > screenWidth){
                mHeightSize = screenWidth;
                mWidthSize = screenWidth;
            }else {
                mWidthSize = mHeightSize;
            }
        }else if (mHM == MeasureSpec.AT_MOST){
            mHeightSize = mWidthSize;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.CYAN);
        int saveCount = canvas.getSaveCount();
//        canvas.saveLayer()
// Canvas可以绘制的对象有：弧线(arcs)、填充颜色(argb和color)、 Bitmap、圆(circle和oval)、点(point)、
// 线(line)、矩形(Rect)、图片(Picture)、圆角矩形 (RoundRect)、文本(text)、顶点(Vertices)、路径(path)
        int mCSWidth = canvas.getWidth();
        int mCSHeight = canvas.getHeight();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        canvas.translate(mCSWidth/2, mCSHeight/2); //将位置移动画纸的坐标点:150,150
        int radus = 0;
        if (canvas.getWidth() > canvas.getHeight()){
            canvas.drawCircle(0, 0,mCSHeight/2-100, paint);
            radus = mCSHeight/2-100;
        }else {
            canvas.drawCircle(0, 0, mCSWidth/2-100, paint); //画圆圈
            radus = mCSWidth/2-100;
        }

        //使用path绘制路径文字
        canvas.save();
        canvas.translate(-radus +100, -radus +100);
        Path path = new Path();
        path.addArc(new RectF(0,0,2*radus-200,2*radus-200), -180, 180);
        Paint citePaint = new Paint();
        citePaint.setAntiAlias(true);
        citePaint.setColor(Color.BLACK);
        citePaint.setStyle(Paint.Style.FILL);
        citePaint.setTextSize(34);
        citePaint.setStrokeWidth(4);
        citePaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawTextOnPath("自定义写的时钟，能转动起来的哦", path, 0, 0, citePaint);
        canvas.restore();
//        canvas.restore();

        Paint tmpPaint = new Paint(paint); //小刻度画笔对象
        tmpPaint.setStrokeWidth(3);
        tmpPaint.setColor(Color.RED);
        tmpPaint.setTextAlign(Paint.Align.CENTER);
        tmpPaint.setTextSize(20);

        float y =100;
        int count = 60; //总刻度数
//        int mPW =
        int mPH = mCSHeight/2 - radus;

        for(int i=0 ; i <count ; i++){
            if(i%5 == 0){
                canvas.drawLine(0, -radus, 0, -radus +12f, paint);
                canvas.drawText(String.valueOf(i/5), 0, -radus+35f, tmpPaint);

            }else{
                canvas.drawLine(0, -radus, 0, -radus+6f, tmpPaint);
            }
            canvas.rotate(360/count,0,0); //旋转画纸
        }

        //绘制指针
        citePaint.setColor(Color.BLUE);
        citePaint.setStrokeWidth(6);
        canvas.drawLine((float) (45f*Math.sin(Math.toRadians(360-6*2))),(float) (45f*Math.cos(Math.toRadians(360 - 6*2))),(float) ((radus - 80f)*Math.sin(Math.toRadians(180-6*2))),(float) ((radus-80f)*Math.cos(Math.toRadians(180-6*2))),citePaint);
        tmpPaint.setColor(Color.GRAY);
        tmpPaint.setStrokeWidth(4);
        canvas.drawCircle(0,0, 7, tmpPaint);
        tmpPaint.setStyle(Paint.Style.FILL);
        tmpPaint.setColor(Color.YELLOW);
        canvas.drawCircle(0,0, 5, tmpPaint);
        canvas.drawLine(0, 65, 0, -radus +30, paint);
    }
}
