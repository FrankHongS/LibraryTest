package com.hon.librarytest.diffutil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.hon.librarytest.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Frank on 2018/9/9.
 * E-mail:frank_hon@foxmail.com
 */

public class DiffUtilActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private Button mUpdateButton;

    private DiffAdapter mDiffAdapter;
    private String[] mDataList={
            "Chandler Bing",
            "Reachel Green",
            "Ross Geller"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diff_util);

        initViews();
    }

    private void initViews() {
        mRecyclerView=findViewById(R.id.rv_diff);
        mUpdateButton=findViewById(R.id.btn_update);

        mDiffAdapter=new DiffAdapter(this, Arrays.asList(mDataList));
        mRecyclerView.setAdapter(mDiffAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        mUpdateButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mDiffAdapter.replace(Arrays.asList(
                "Chandler Bing",
                "Reachel Green",
                "c",
                "d",
                "e",
                "f"
        ));
    }
}
