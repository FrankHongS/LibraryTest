package com.hon.photopreviewlayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;

/**
 * Created by Frank on 2018/5/27.
 * E-mail:frank_hon@foxmail.com
 */

public class PhotoView extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    private static final String TAG = PhotoView.class.getSimpleName();

    private Drawable mOriginalDrawable=null;

    // initial scale
    private float mInitScale=-1;
    // maximum scale
    private float mMaxScale;

    private Matrix mScaleMatrix;

    private ScaleGestureDetector mScaleGestureDetector;

    // pointer count in last time
    private int mLastPointerCount = 0;
    private float mLastX = 0f;
    private float mLastY = 0f;
    private boolean mCanMove = false;

    private float mTouchSlop;
    // double tap
    private GestureDetector mGestureDetector;

    public PhotoView(Context context) {
        this(context, null);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setScaleType(ScaleType.MATRIX);
        mScaleMatrix = new Matrix();
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                resetDrawable();
                return true;
            }
        });
        setOnTouchListener(this);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @TargetApi(16)
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {

        int width = getWidth();
        int height = getHeight();

        Drawable d = getDrawable();

        if (d == null)
            return;

        if(mOriginalDrawable==d)
            return;

        mOriginalDrawable=d;

        int dw = d.getIntrinsicWidth();
        int dh = d.getIntrinsicHeight();

        float scale = Math.min(width / (float) dw, height / (float) dh);


        mScaleMatrix.reset();

        int dx = width / 2 - dw / 2;
        int dy = height / 2 - dh / 2;

        mScaleMatrix.postTranslate(dx, dy);
        mScaleMatrix.postScale(scale, scale, width / 2f, height / 2f);
        setImageMatrix(mScaleMatrix);

        mInitScale = scale;
        mMaxScale = 4 * scale;

    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {

        float currentScale = getCurrentScale();
        float scaleFactor = detector.getScaleFactor();

        if (scaleFactor * currentScale < mInitScale) {
            scaleFactor = mInitScale / currentScale;
        } else if (scaleFactor * currentScale > mMaxScale) {
            scaleFactor = mMaxScale / currentScale;
        }

        mScaleMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

        fitDrawableCenter();

        setImageMatrix(mScaleMatrix);

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (getDrawable() == null)
            return false;
        // GestureDetector for double tap
        if (mGestureDetector.onTouchEvent(event))
            return true;
        // ScaleGestureDetector for scaling
        mScaleGestureDetector.onTouchEvent(event);

        // dispose touch event for translating
        translateOnTouchEvent(event);

        return true;
    }

    /**
     * dispose touch event for translating
     *
     * @param event motionEvent
     */
    private void translateOnTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();

        float x = 0f;
        float y = 0f;

        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }

        x /= pointerCount;
        y /= pointerCount;

        if (pointerCount != mLastPointerCount) {
            mCanMove = false;
            mLastX = x;
            mLastY = y;
        }

        mLastPointerCount = pointerCount;

        RectF rect = getDrawableRectF();

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                if(rect.width()>getWidth()||rect.height()>getHeight())
                    getParent().requestDisallowInterceptTouchEvent(true);

                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                if(rect.width()>getWidth()||rect.height()>getHeight())
                    getParent().requestDisallowInterceptTouchEvent(true);

                float dx = x - mLastX;
                float dy = y - mLastY;

//                if(rect.right-getWidth()>0.01&&dx<=0){
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }
//
//                if(rect.left<-0.01&&dx>=0){
//                    getParent().requestDisallowInterceptTouchEvent(true);
//                }


                if (!mCanMove) {
                    mCanMove = checkIfCanMove(dx, dy);
                }

                if (mCanMove) {

                    float width = getWidth();
                    float height = getHeight();

                    if (dx < -(rect.right - width))
                        dx = -(rect.right - width);
                    else if (dx > -rect.left)
                        dx = -rect.left;

                    if (dy < -(rect.bottom - height))
                        dy = -(rect.bottom - height);
                    else if (dy > -rect.top)
                        dy = -rect.top;

                    if (rect.width() <= width)
                        dx = 0f;
                    if (rect.height() <= height)
                        dy = 0f;

                    mScaleMatrix.postTranslate(dx, dy);
                    setImageMatrix(mScaleMatrix);
                }

                mLastX = x;
                mLastY = y;

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;
            default:
                break;
        }

    }

    private float getCurrentScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    /**
     * 如果放缩后的图片大于控件的宽或高，当上下左右出现空白，进行相应平移；
     * 如果放缩后的图片小于或等于空间的宽或高，将图片平移至中央
     */
    private void fitDrawableCenter() {

        RectF rect = getDrawableRectF();

        float deltaX = 0f;
        float deltaY = 0f;

        float width = getWidth();
        float height = getHeight();

        if (rect.width() > width) {
            if (rect.left > 0)
                deltaX = -rect.left;
            if (rect.right < width)
                deltaX = width - rect.right;
        } else {
            deltaX = width / 2 - rect.right + rect.width() / 2;
        }

        if (rect.height() > height) {
            if (rect.top > 0)
                deltaY = -rect.top;
            if (rect.bottom < height)
                deltaY = height - rect.bottom;
        } else {
            deltaY = height / 2 - rect.bottom + rect.height() / 2;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    public RectF getDrawableRectF() {
        RectF rect = new RectF();
        Drawable d = getDrawable();

        if (d != null) {
            rect.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            mScaleMatrix.mapRect(rect);
        }

        return rect;
    }

    private boolean checkIfCanMove(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }


    private void resetDrawable() {

        float width = getWidth();
        float height = getHeight();

        float scaleFactor = mInitScale / getCurrentScale();

        RectF rect = getDrawableRectF();
        float dx = width / 2 - (rect.left + rect.right) / 2;
        float dy = height / 2 - (rect.top + rect.bottom) / 2;

        mScaleMatrix.postTranslate(dx, dy);
        mScaleMatrix.postScale(scaleFactor, scaleFactor, width / 2, height / 2);

        setImageMatrix(mScaleMatrix);
    }
}

