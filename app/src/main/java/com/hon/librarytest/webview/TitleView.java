package com.hon.librarytest.webview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Frank on 2018/3/10.
 * E-mail:frank_hon@foxmail.com
 */

public class TitleView extends View {

    private Paint mLinePaint;
    private Paint mTopPaint;
    private Paint mBottomPaint;

    private int mTopColorId;
    private int mBottomColorId;
    private int mMiddleLineColorId;

    public TitleView(@NonNull Context context) {
        this(context,null);
    }

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.TitleView);
        mTopColorId=a.getResourceId(R.styleable.TitleView_topColor,R.color.titleViewDefaultColor);
        mBottomColorId=a.getResourceId(R.styleable.TitleView_bottomColor,R.color.titleViewDefaultColor);
        mMiddleLineColorId=a.getResourceId(R.styleable.TitleView_middleLineColor,R.color.titleViewDefaultColor);
        a.recycle();

        initPaints();
    }

    private void initPaints() {
        mLinePaint=new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(getResources().getColor(mMiddleLineColorId));

        mTopPaint=new Paint();
        mTopPaint.setAntiAlias(true);
        mTopPaint.setColor(getResources().getColor(mTopColorId));

        mBottomPaint=new Paint();
        mBottomPaint.setAntiAlias(true);
        mBottomPaint.setColor(getResources().getColor(mBottomColorId));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float width=canvas.getWidth();
        float height=canvas.getHeight();

        canvas.translate(width/2,height/2);

        canvas.drawLine(-width/2,0,width/2,0,mLinePaint);

        canvas.drawRect(-width/2,-height/2,width/2,0,mTopPaint);
        canvas.drawRect(-width/2,0,width/2,height/2,mBottomPaint);
    }
}
