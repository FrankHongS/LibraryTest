package com.hon.librarytest.popup;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hon.librarytest.R;

/**
 * Created by Frank_Hon on 9/14/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class MyPopupWindow extends PopupWindow {

    private static final String TAG = XIPopupWindow.class.getSimpleName();

    private IXIPopupMenuListener mPopupMenuListener = null;
    private XIMessage mMessage = null;

    private TextView mCopyText = null;
    private TextView mDeleteText = null;
    private TextView mShareText = null;

    private int mContentViewWidth;
    private int mContentViewHeight;

    private int[] mOffset = new int[2];

    public MyPopupWindow(Context context) {
        this(LayoutInflater.from(context).inflate(R.layout.xichat_popup_menu, null));

        initView(getContentView());
    }

    public MyPopupWindow(View contentView) {
        super(contentView);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setOutsideTouchable(true);

        measure();

        mContentViewWidth = contentView.getMeasuredWidth();
        mContentViewHeight = contentView.getMeasuredHeight();
    }

    private void initView(final View view) {

        mCopyText = view.findViewById(R.id.xichat_popup_menu_copy);
        mDeleteText = view.findViewById(R.id.xichat_popup_menu_delete);
        mShareText = view.findViewById(R.id.xichat_popup_menu_share);

        mCopyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupMenuListener != null && mMessage != null) {
                    mPopupMenuListener.onCopy(v, mMessage);
                }
                dismiss();
            }
        });
        mDeleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupMenuListener != null && mMessage != null) {
                    mPopupMenuListener.onDelete(view, mMessage);
                }
                dismiss();
            }
        });
        mShareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupMenuListener != null && mMessage != null) {
                    mPopupMenuListener.onShare(v, mMessage);
                }
                dismiss();
            }
        });
    }


    /**
     * default gravity is bottom|left
     *
     * @param anchor anchor view
     */
    @Override
    public void showAsDropDown(View anchor) {

        mOffset[0] = anchor.getWidth() / 2 - mContentViewWidth;
        mOffset[1] = -anchor.getHeight() / 2;

        checkBound(mOffset, anchor);

        super.showAsDropDown(anchor, mOffset[0], mOffset[1]);

        circularReveal(anchor);
    }

    private void checkBound(int[] offset, View anchor) {
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        boolean top = XIScreenUtil.getScreenHeight(anchor.getContext()) - location[1] + mOffset[1] < getContentViewHeight();

        XIChatUILogManager.getInstance().d(TAG, "checkBound: \n" +
                "screen height: " + XIScreenUtil.getScreenHeight(anchor.getContext()) + "\n" +
                "anchor position Y: " + location[1] + "\n" +
                "offset Y: " + mOffset[1] + "\n" +
                "height: " + getContentView().getHeight() + "\n" +
                "content view height: " + getContentViewHeight());
        if (top) {
            offset[1] = -getContentViewHeight() + mOffset[1];
        }
    }

    private int getContentViewHeight() {
        return getContentView().getMeasuredHeight() <= 0 ? mContentViewHeight : getContentView().getMeasuredHeight();
    }

    private int getContentViewWidth() {
        return getContentView().getWidth() <= 0 ? mContentViewWidth : getContentView().getWidth();
    }

    /**
     * invoke view.measure() to get measured width and height
     */
    private void measure() {
        getContentView().measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularReveal(@NonNull final View anchor) {
        final View contentView = getContentView();
        contentView.post(new Runnable() {
            @Override
            public void run() {
                final int[] myLocation = new int[2];
                final int[] anchorLocation = new int[2];
                contentView.getLocationOnScreen(myLocation);
                anchor.getLocationOnScreen(anchorLocation);
                final int cx = anchorLocation[0] - myLocation[0] + anchor.getWidth() / 2;
                final int cy = anchorLocation[1] - myLocation[1] + anchor.getHeight() / 2;

                contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                final int dx = Math.max(cx, contentView.getMeasuredWidth() - cx);
                final int dy = Math.max(cy, contentView.getMeasuredHeight() - cy);
                final float finalRadius = (float) Math.hypot(dx, dy);
                Animator animator = ViewAnimationUtils.createCircularReveal(contentView, cx, cy, 0f, finalRadius);
                animator.setDuration(300);
                animator.start();
            }
        });
    }

    public void setPopupMenuListener(IXIPopupMenuListener popupMenuListener) {
        this.mPopupMenuListener = popupMenuListener;
    }

    public void setMessage(XIMessage message) {
        this.mMessage = message;
    }

    public void setCopyVisible(boolean visibility) {
        if (mCopyText != null) {
            mCopyText.setVisibility(visibility ? View.VISIBLE : View.GONE);
            measure();
        }
    }

    public void setDeleteVisible(boolean visibility) {
        if (mDeleteText != null) {
            mDeleteText.setVisibility(visibility ? View.VISIBLE : View.GONE);
            measure();
        }
    }

    public void setShareVisible(boolean visibility) {
        if (mShareText != null) {
            mShareText.setVisibility(visibility ? View.VISIBLE : View.GONE);
            measure();
        }
    }

    public void reset() {
        if (mCopyText != null) {
            mCopyText.setVisibility(View.VISIBLE);
        }
        if (mDeleteText != null) {
            mDeleteText.setVisibility(View.VISIBLE);
        }
        if (mShareText != null) {
            mShareText.setVisibility(View.VISIBLE);
        }

        measure();
    }
}
