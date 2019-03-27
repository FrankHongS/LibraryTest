package com.hon.librarytest.pagerecyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.hon.librarytest.R;
import com.hon.optimizedrecyclerviewlib.PageRecyclerView;
import com.hon.optimizedrecyclerviewlib.item.PageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frank_Hon on 3/22/2019.
 * E-mail: v-shhong@microsoft.com
 */
@SuppressWarnings("all")
public class PageRecyclerViewActivity extends AppCompatActivity {

    private static final String TAG = PageRecyclerViewActivity.class.getSimpleName();

    private PageRecyclerView recyclerView;

    private MyAdapter mAdapter;

    private static final int ARRAY_LENGTH = 20;
    private List<PageItem> itemList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_recycler);

        for(int i=0;i<ARRAY_LENGTH;i++){
            itemList.add(new NormalItem(i+""));
        }

        initView();

    }

    private void initView() {
        recyclerView=findViewById(R.id.orv_page);

        mAdapter=new MyAdapter(this, itemList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        recyclerView.setOnLoadMoreListener(new PageRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Log.d(TAG, "onLoadMore: ");
                Toast.makeText(PageRecyclerViewActivity.this, "onLoadMore", Toast.LENGTH_SHORT).show();

                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.hideLoading();

                        for(int i=0;i<5;i++){
                            itemList.add(new NormalItem((i+20)+""));
                        }

                        mAdapter.notifyItemRangeInserted(itemList.size()-5,5);
                    }
                },3000);
            }
        });
    }
}
