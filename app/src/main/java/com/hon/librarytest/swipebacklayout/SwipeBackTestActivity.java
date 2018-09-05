package com.hon.librarytest.swipebacklayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Window;

import com.hon.librarytest.R;
import com.hon.swipebacklayout.SwipeBackLayout;

/**
 * Created by Frank on 2018/4/25.
 * E-mail:frank_hon@foxmail.com
 */

public class SwipeBackTestActivity extends SwipeBackActivity{

//    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_swipe_back_test);
        getSwipeBackLayout().setEdgeTrackingEnabled(me.imid.swipebacklayout.lib.SwipeBackLayout.EDGE_LEFT);
//        mSwipeBackLayout= (SwipeBackLayout) LayoutInflater.from(this)
//                .inflate(com.hon.swipebacklayout.R.layout.layout_swipe_back,null);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        mSwipeBackLayout.attachToActivity(this);
    }
}
