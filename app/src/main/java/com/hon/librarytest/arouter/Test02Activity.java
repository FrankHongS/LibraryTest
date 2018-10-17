package com.hon.librarytest.arouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hon.librarytest.R;

/**
 * Created by Frank_Hon on 10/16/2018.
 * E-mail: v-shhong@microsoft.com
 */
@Route(path = "/test/test02")
public class Test02Activity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_route);
    }
}
