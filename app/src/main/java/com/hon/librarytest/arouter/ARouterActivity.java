package com.hon.librarytest.arouter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hon.librarytest.R;

/**
 * Created by Frank_Hon on 10/11/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class ARouterActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arouter);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_route:
                ARouter.getInstance().build("/test/activity")
                        .navigation();
                break;
            case R.id.btn_uri:
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("arouter://m.aliyun.com/test/test02"));
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}
