package com.hon.librarytest.pagerecyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hon.librarytest.R;
import com.hon.optimizedrecyclerviewlib.OptimizedRecyclerView;

import java.util.Arrays;

/**
 * Created by Frank_Hon on 3/22/2019.
 * E-mail: v-shhong@microsoft.com
 */
@SuppressWarnings("all")
public class PageRecyclerViewActivity extends AppCompatActivity {

    private OptimizedRecyclerView recyclerView;

    private PageRecyclerAdapter mAdapter;

    private static final int ARRAY_LENGTH = 20;
    private String[] strArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_recycler);

        strArray=new String[ARRAY_LENGTH];
        for(int i=0;i<ARRAY_LENGTH;i++){
            strArray[i]=i+"";
        }

        initView();

    }

    private void initView() {
        recyclerView=findViewById(R.id.orv_page);

        mAdapter=new PageRecyclerAdapter(this, Arrays.asList(strArray));
        recyclerView.setAdapterWithLoading(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setOnLoadMoreListener(new OptimizedRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Toast.makeText(PageRecyclerViewActivity.this, "onLoadMore", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
