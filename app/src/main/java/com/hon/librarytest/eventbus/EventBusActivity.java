package com.hon.librarytest.eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.hon.librarytest.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Frank on 2018/4/16.
 * E-mail:frank_hon@foxmail.com
 */

public class EventBusActivity extends AppCompatActivity{

    private Button mButton;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void initViews() {
        mButton=findViewById(R.id.btn_test);
        mTextView=findViewById(R.id.tv_test);

        mButton.setOnClickListener(view->{
            TestEvent event=new TestEvent();
            event.setMsg("hello world !");
            EventBus.getDefault().post(event);
        });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTestEvent(TestEvent event){
        mTextView.setText(event.getMsg());
    }
}
