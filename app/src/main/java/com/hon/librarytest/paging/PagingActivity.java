package com.hon.librarytest.paging;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.hon.librarytest.R;

/**
 * Created by Frank_Hon on 9/21/2018.
 * E-mail: v-shhong@microsoft.com
 */
public class PagingActivity extends AppCompatActivity{

    private RecyclerView mCheeseList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paging);

        initView();
    }

    private void initView() {
        mCheeseList=findViewById(R.id.cheeseList);

    }
}
