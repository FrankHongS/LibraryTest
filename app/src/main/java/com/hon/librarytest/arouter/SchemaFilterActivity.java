package com.hon.librarytest.arouter;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by Frank_Hon on 10/16/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class SchemaFilterActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri uri = getIntent().getData();
        ARouter.getInstance().build(uri).navigation();
        finish();
    }
}
